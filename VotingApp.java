package hellotvxlet;

import java.awt.Color;
import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.tv.xlet.*;
import org.bluray.ui.event.HRcEvent;
import org.davic.resources.ResourceClient;
import org.davic.resources.ResourceProxy;
import org.dvb.event.EventManager;
import org.dvb.event.UserEvent;
import org.dvb.event.UserEventListener;
import org.dvb.event.UserEventRepository;
import org.havi.ui.HBackgroundConfigTemplate;
import org.havi.ui.HBackgroundDevice;
import org.havi.ui.HBackgroundImage;
import org.havi.ui.HScene;
import org.havi.ui.HSceneFactory;
import org.havi.ui.HTextButton;
import org.havi.ui.HScreen;
import org.havi.ui.HStaticText;
import org.havi.ui.HStillImageBackgroundConfiguration;



//import + implement all abstract... (om ResourceClient te gebruiken)

import org.havi.ui.HVisible;
import org.havi.ui.event.HBackgroundImageEvent;
import org.havi.ui.event.HBackgroundImageListener;


public class VotingApp implements Xlet, ResourceClient, HBackgroundImageListener,
UserEventListener{
   
 HScreen screen;  //Dit maakt enkel de reference, object aanmaken via new
                //Hier gezet om globale/klasse variable te hebben
 HBackgroundDevice bgDevice;
 HBackgroundConfigTemplate bgTemplate;
 HStillImageBackgroundConfiguration bgConfiguration;
 
 //C:\Program Files\TechnoTrend\TT-MHP-Browser\fileio\DSMCC\0.0.3
 HBackgroundImage image[]=new HBackgroundImage[10];
 //HBackgroundImage bgImage = new HBackgroundImage("Achtergrondafbeelding.jpg");
 int geladen =0;
 int huidig = 0;
    private HScene scene;

    private HStaticText hstTrumpVotes;
    private HStaticText hstWestVotes;
    private HStaticText hstClintonVotes;
    private HStaticText hstPerlmanVotes;
    
    private int pageCounter;
    private int voteCounterTrump;
    private int voteCounterWest;
    private int voteCounterClinton;
    private int voteCounterPerlman;
  
    public VotingApp() {
        
    }

    public void initXlet(XletContext context) throws XletStateChangeException{
      //Alle initialisaties
        screen = HScreen.getDefaultHScreen();
        bgDevice = screen.getDefaultHBackgroundDevice();
        if(bgDevice.reserveDevice(this)){

            System.out.println("Background image device has been reserved");

        }
        else{
            System.out.println("Background image device cannot be reserved");
        }      
       bgTemplate = new HBackgroundConfigTemplate();
       bgTemplate.setPreference(HBackgroundConfigTemplate.STILL_IMAGE, 
               HBackgroundConfigTemplate.REQUIRED);
       
       bgConfiguration = (HStillImageBackgroundConfiguration) 
               bgDevice.getBestConfiguration(bgTemplate);
               //bgDevice.getBestConfiguration(bgTemplate);
       try{
        bgDevice.setBackgroundConfiguration(bgConfiguration);
    }
       catch(Exception ex){
           ex.printStackTrace();
       }
       image[0]=new HBackgroundImage("VotingCenter1.jpg");
       image[1]=new HBackgroundImage("VotingCenter2.jpg");
       image[2]=new HBackgroundImage("Democrats1.jpg");
       image[3]=new HBackgroundImage("Democrats2.jpg");
       image[4]=new HBackgroundImage("HillaryClinton.jpg");
       image[5]=new HBackgroundImage("RonPerlman.jpg");
       image[6]=new HBackgroundImage("Republicans1.jpg");
       image[7]=new HBackgroundImage("Republicans2.jpg");
       image[8]=new HBackgroundImage("DonaldTrump.jpg");
       image[9]=new HBackgroundImage("KanyeWest.jpg");
       for(int i=0;i<10;i++)
        { 
            image[i].load(this);
             System.out.println("Image:" + image[i]);
        }
       
       UserEventRepository repo = new UserEventRepository("naam");
       repo.addAllArrowKeys();
       repo.addKey(HRcEvent.VK_ENTER);
       repo.addKey(HRcEvent.VK_0);
       EventManager.getInstance().addUserEventListener(this, repo);
       //public class HelloTVXlet implements Xlet, ResourceClient, 
       //HBackgroundImageListener,UserEventListener{
       //import + implement all
       
       
       scene = HSceneFactory.getInstance().getDefaultHScene();
       //globaal:
       //  private HScene scene;
       //private HStaticText hst
       
       voteCounterTrump = 0;
       voteCounterWest = 0;
       voteCounterClinton = 0;
       voteCounterPerlman = 0;
       
       hstTrumpVotes = new HStaticText(Integer.toString(voteCounterTrump),200,410,100,40); //x,y,w,h
       hstTrumpVotes.setVerticalAlignment(HStaticText.VALIGN_TOP);
       hstTrumpVotes.setHorizontalAlignment(HStaticText.HALIGN_CENTER);
       hstTrumpVotes.setBackgroundMode(HVisible.BACKGROUND_FILL);
       hstTrumpVotes.setBackground(Color.RED);
       //in UserEventReceived
       scene.add(hstTrumpVotes);
       
       hstWestVotes = new HStaticText(Integer.toString(voteCounterWest),200,460,100,40); //x,y,w,h
       hstWestVotes.setVerticalAlignment(HStaticText.VALIGN_TOP);
       hstWestVotes.setHorizontalAlignment(HStaticText.HALIGN_CENTER);
       hstWestVotes.setBackgroundMode(HVisible.BACKGROUND_FILL);
       hstWestVotes.setBackground(Color.RED);
       //in UserEventReceived
       scene.add(hstWestVotes);
       
       hstClintonVotes = new HStaticText(Integer.toString(voteCounterClinton),570,410,100,40); //x,y,w,h
       hstClintonVotes.setVerticalAlignment(HStaticText.VALIGN_TOP);
       hstClintonVotes.setHorizontalAlignment(HStaticText.HALIGN_CENTER);
       hstClintonVotes.setBackgroundMode(HVisible.BACKGROUND_FILL);
       hstClintonVotes.setBackground(Color.BLUE);
       //in UserEventReceived
       scene.add(hstClintonVotes);
       
       hstPerlmanVotes = new HStaticText(Integer.toString(voteCounterPerlman),570,460,100,40); //x,y,w,h
       hstPerlmanVotes.setVerticalAlignment(HStaticText.VALIGN_TOP);
       hstPerlmanVotes.setHorizontalAlignment(HStaticText.HALIGN_CENTER);
       hstPerlmanVotes.setBackgroundMode(HVisible.BACKGROUND_FILL);
       hstPerlmanVotes.setBackground(Color.BLUE);
       //in UserEventReceived
       scene.add(hstPerlmanVotes);
       
       pageCounter = 0;

       try {
       bgConfiguration.displayImage(image[pageCounter]);
       System.out.println("!!Background Image Loaded!!");
       }
       catch(Exception ex){
          ex.printStackTrace();
          System.out.println("!!Background image could not be loaded!!");
       }
       scene.validate();
       scene.setVisible(true);
    }
    
    
    public void imageLoaded(HBackgroundImageEvent e) {
        geladen++;
        System.out.println("Images geladen: " + geladen);
        if (geladen ==9){
            try{
                bgConfiguration.displayImage(image[0]);
                pageCounter = 0;
                enableLabels();
                //bgConfiguration.displayImage(bgImage);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    public void imageLoadFailed(HBackgroundImageEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void userEventReceived(UserEvent e) {
      //  System.out.println("!!CurrentPage:" + pageCounter);
        //        System.out.println("!!CurrentPageEntered:" + pageCounter);
        if(e.getType()==HRcEvent.KEY_PRESSED)
        {
            if(e.getCode()==HRcEvent.VK_ENTER)
            {
                switch (pageCounter)
                {
                    case 0:
                        pageCounter = 6;
                        break;
                    case 1:
                        pageCounter = 2;
                        break;
                        
                    case 2:                   
                        hstClintonVotes.setTextContent(String.valueOf(voteCounterClinton), HVisible.NORMAL_STATE);
                        voteCounterClinton++;
                        hstClintonVotes = new HStaticText(Integer.toString(voteCounterClinton),570,410,100,40); //x,y,w,h
                        hstClintonVotes.setVerticalAlignment(HStaticText.VALIGN_TOP);
                        hstClintonVotes.setHorizontalAlignment(HStaticText.HALIGN_CENTER);
                        hstClintonVotes.setBackgroundMode(HVisible.BACKGROUND_FILL);
                        hstClintonVotes.setBackground(Color.BLUE);
                        scene.add(hstClintonVotes);
                        System.out.println("StemHillary");
                        break;
                        
                    case 3:
                        hstPerlmanVotes.setTextContent(String.valueOf(voteCounterPerlman), HVisible.NORMAL_STATE);
                        voteCounterPerlman++;  
                        hstPerlmanVotes = new HStaticText(Integer.toString(voteCounterPerlman),570,460,100,40); //x,y,w,h
                        hstPerlmanVotes.setVerticalAlignment(HStaticText.VALIGN_TOP);
                        hstPerlmanVotes.setHorizontalAlignment(HStaticText.HALIGN_CENTER);
                        hstPerlmanVotes.setBackgroundMode(HVisible.BACKGROUND_FILL);
                        hstPerlmanVotes.setBackground(Color.BLUE);
                        scene.add(hstPerlmanVotes);
                        System.out.println("StemRon");
                        break;
                        
                    case 6:
                        hstTrumpVotes.setTextContent(String.valueOf(voteCounterTrump), HVisible.NORMAL_STATE);
                        voteCounterTrump++;
                        hstTrumpVotes = new HStaticText(Integer.toString(voteCounterTrump),200,410,100,40); //x,y,w,h
                        hstTrumpVotes.setVerticalAlignment(HStaticText.VALIGN_TOP);
                        hstTrumpVotes.setHorizontalAlignment(HStaticText.HALIGN_CENTER);
                        hstTrumpVotes.setBackgroundMode(HVisible.BACKGROUND_FILL);
                        hstTrumpVotes.setBackground(Color.RED);
                        scene.add(hstTrumpVotes);
                        System.out.println("StemTrump");
                        break;
                        
                        
                    case 7:
                        hstWestVotes.setTextContent(String.valueOf(voteCounterWest), HVisible.NORMAL_STATE);
                        voteCounterWest++;
                        hstWestVotes = new HStaticText(Integer.toString(voteCounterWest),200,460,100,40); //x,y,w,h
                        hstWestVotes.setVerticalAlignment(HStaticText.VALIGN_TOP);
                        hstWestVotes.setHorizontalAlignment(HStaticText.HALIGN_CENTER);
                        hstWestVotes.setBackgroundMode(HVisible.BACKGROUND_FILL);
                        hstWestVotes.setBackground(Color.RED);
                        scene.add(hstWestVotes);
                        System.out.println("StemKanye");
                        break;        
                }   
            }
            
            if(((e.getCode()==HRcEvent.VK_RIGHT)))
            {
                 switch (pageCounter){
                     case 0:
                            pageCounter = 1;
                            break;
                            
                     case 2:
                         pageCounter = 4;
                         break;
                         
                     case 3:
                         pageCounter = 5;
                         break;
                         
                     case 6:
                         pageCounter = 8;
                         break;
                         
                     case 7:
                         pageCounter = 9;
                         break;
                 }            
             }
            
             if(((e.getCode()==HRcEvent.VK_LEFT)))
             {
                 switch (pageCounter){
                     case 1:
                            pageCounter = 0;
                            break;
                            
                     case 2:
                         pageCounter = 0;
                         break;
                         
                     case 3:
                         pageCounter = 0;
                         break;
                            
                     case 4:
                         pageCounter = 2;
                         break;
                         
                     case 5:
                         pageCounter = 3;
                         break;
                         
                     case 6:
                         pageCounter = 0;
                         break;
                         
                     case 7:
                         pageCounter = 0;
                         break;
                         
                     case 8:
                         pageCounter = 6;
                         break;
                         
                     case 9:
                         pageCounter = 7;
                         break;                            
                 }
             }
            
             if(((e.getCode() == HRcEvent.VK_DOWN)))
             {
                   switch (pageCounter){
                       case 2:
                           pageCounter = 3;
                           break;
                       
                       case 6:
                           pageCounter = 7;
                           break;     
                   }
             }
            
             if(((e.getCode() == HRcEvent.VK_UP)))
             {
                 switch (pageCounter){
                     case 3:
                         pageCounter = 2;
                         break;
                     
                     case 7:
                         pageCounter = 6;
                         break;                       
                 }
             }
            
            if (pageCounter < 2)
            {
                enableLabels();
            }
            else
            {
                disableLabels();
            }
            
            try
            {
            System.out.println("!!Loading bg!!");
            bgConfiguration.displayImage(image[pageCounter]);
            }
            
            catch(Exception ex)
            {
            ex.printStackTrace();
            System.out.println("!!Background image could not be loaded!!");
            }
        }
    }
    
    public void enableLabels() {
       hstTrumpVotes.setVisible(true);
       hstWestVotes.setVisible(true);
       hstClintonVotes.setVisible(true);
       hstPerlmanVotes.setVisible(true);
    }
    
    public void disableLabels() {
       hstTrumpVotes.setVisible(false);
       hstWestVotes.setVisible(false);
       hstClintonVotes.setVisible(false);
       hstPerlmanVotes.setVisible(false);
    }
    
    public void startXlet() {
        //Al de andere code
        
        
    }

    public void pauseXlet() {
     
    }

    public void destroyXlet(boolean unconditional) {
     
    }

    public void actionPerformed(ActionEvent arg0) {
        
    }

    public boolean requestRelease(ResourceProxy proxy, Object requestData) {
        return false;
    }

    public void release(ResourceProxy proxy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void notifyRelease(ResourceProxy proxy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }   
}
