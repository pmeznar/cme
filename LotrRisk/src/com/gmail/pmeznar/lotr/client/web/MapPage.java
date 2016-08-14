package com.gmail.pmeznar.lotr.client.web;

import org.vaadin.gwtgraphics.client.DrawingArea;

import com.gmail.pmeznar.lotr.client.TerritoryBox;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

public class MapPage {
	public static final int NUM_TERRITORIES = 63;
	private TerritoryBox[] tboxes = new TerritoryBox[NUM_TERRITORIES];
	
	public void load(){
		RootPanel.get().clear();
		loadImage();
		loadLocations();
		setUpCanvas();
		
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				PlayerData.get().getAlliance().showInfo();
			}
		};
		
		if (LotrProxy.debug) timer.schedule(1000);
	}
	
	private void setUpCanvas(){
		DrawingArea canvas = new DrawingArea(1250, 1850);
		canvas.getElement().getStyle().setPosition(Position.ABSOLUTE);
		canvas.getElement().getStyle().setLeft(0, Unit.PX);
		canvas.getElement().getStyle().setTop(0, Unit.PX);
	
		for(TerritoryBox box: tboxes){
			box.setChooseArmyLocation();
			canvas.add(box);
			box.setStyle();
		}
		RootPanel.get().add(canvas);
	}
  
	// This is important to use a handler!
	private void loadImage() {
	    final Image img = new Image("/images/map.jpg");
	    RootPanel.get().add(img);
	    img.setVisible(false);
	    
	    img.addLoadHandler(new LoadHandler() {
	      public void onLoad(LoadEvent event) {
	        scale(img);
	      }
	    });
	}

	private void scale(Image img) {
	    ImageData imageData = scaleImage(img, 1);
	    
	    drawToScreen(imageData);
	}

	private ImageData scaleImage(Image image, double scaleToRatio) {
	    
	    Canvas canvasTmp = Canvas.createIfSupported();
	    Context2d context = canvasTmp.getContext2d();

	    double ch = (image.getHeight() * scaleToRatio) + 100;
	    double cw = (image.getWidth() * scaleToRatio) + 100;

	    canvasTmp.setCoordinateSpaceHeight((int) ch);
	    canvasTmp.setCoordinateSpaceWidth((int) cw);
	    
	    ImageElement imageElement = ImageElement.as(image.getElement());
	   
	    // s = source
	    // d = destination 
	    double sx = 0;
	    double sy = 0;
	    double sw = imageElement.getWidth();
	    double sh = imageElement.getHeight();
	    
	    double dx = 0;
	    double dy = 0;
	    double dw = imageElement.getWidth();
	    double dh = imageElement.getHeight();
	    
	    // tell it to scale image
	    context.scale(scaleToRatio, scaleToRatio);
	    
	    // draw image to canvas
	    context.drawImage(imageElement, sx, sy, sw, sh, dx, dy, dw, dh);
	    
	    // get image data
	    double w = dw * scaleToRatio;
	    double h = dh * scaleToRatio;
	    ImageData imageData = context.getImageData(0, 0, w, h);

	    return imageData;
	}

	private void drawToScreen(ImageData imageData) {
	    Canvas canvasTmp = Canvas.createIfSupported();
	    RootPanel.get().add(canvasTmp);
	    canvasTmp.setStyleName("test1");
	    canvasTmp.setCoordinateSpaceHeight((int) imageData.getHeight());
	    canvasTmp.setCoordinateSpaceWidth((int) imageData.getWidth());
	    Context2d context = canvasTmp.getContext2d();
	    context.putImageData(imageData, 0, 0);
	    canvasTmp.getElement().getStyle().setFloat(Float.LEFT);
	}
	
	private void loadLocations(){
        tboxes[0] = new TerritoryBox(60, 390, "Forlindon");
        tboxes[1] = new TerritoryBox(150, 380, "Mithlond");
        tboxes[2] = new TerritoryBox(118, 575, "Harlindon");
        tboxes[3] = new TerritoryBox(220, 330, "Lune Valley");
        tboxes[4] = new TerritoryBox(350, 270, "Evendim Hills");
        tboxes[5] = new TerritoryBox(315, 390, "Tower Hills");
        tboxes[6] = new TerritoryBox(330, 530, "The Shire");
        tboxes[7] = new TerritoryBox(675, 70, "Forodwaith");
        tboxes[8] = new TerritoryBox(760, 120, "Eastern Angmar");
        tboxes[9] = new TerritoryBox(500, 170, "Borderlands");
        tboxes[10] = new TerritoryBox(600, 190, "Angmar");
        tboxes[11] = new TerritoryBox(470, 300, "North Downs");
        tboxes[12] = new TerritoryBox(445, 380, "Fornost");
        tboxes[13] = new TerritoryBox(560, 420, "Weather Hills");
        tboxes[14] = new TerritoryBox(700, 360, "Rhudaur");
        tboxes[15] = new TerritoryBox(398, 455, "Buckland");
        tboxes[16] = new TerritoryBox(485, 485, "Old Forest");
        tboxes[17] = new TerritoryBox(455, 575, "South Downs");
        tboxes[18] = new TerritoryBox(1130, 10, "North Rhun");
        tboxes[19] = new TerritoryBox(1035, 110, "Withered Heath");
        tboxes[20] = new TerritoryBox(1030, 305, "Esgaroth");
        tboxes[21] = new TerritoryBox(1150, 415, "South Rhun");
        tboxes[22] = new TerritoryBox(810, 255, "Carrock");
        tboxes[23] = new TerritoryBox(920, 270, "North Mirkwood");
        tboxes[24] = new TerritoryBox(860, 485, "Anduin Valley");
        tboxes[25] = new TerritoryBox(950, 490, "Eastern Mirkwood");
        tboxes[26] = new TerritoryBox(945, 710, "South Mirkwood");
        tboxes[27] = new TerritoryBox(610, 580, "Eregion");
        tboxes[28] = new TerritoryBox(340, 710, "Minhiriath");
        tboxes[29] = new TerritoryBox(540, 750, "Dunland");
        tboxes[30] = new TerritoryBox(470, 920, "Enedwaith");
        tboxes[31] = new TerritoryBox(665, 950, "Fangorn");
        tboxes[32] = new TerritoryBox(440, 1110, "West Rohan");
        tboxes[33] = new TerritoryBox(665, 1090, "Gap of Rohan");
        tboxes[34] = new TerritoryBox(690, 615, "Moria");
        tboxes[35] = new TerritoryBox(795, 580, "Gladden Fields");
        tboxes[36] = new TerritoryBox(740, 765, "Lorien");
        tboxes[37] = new TerritoryBox(800, 940, "The Wold");
        tboxes[38] = new TerritoryBox(885, 1070, "Dead Marshes");
        tboxes[39] = new TerritoryBox(875, 885, "Emyn Muil");
        tboxes[40] = new TerritoryBox(1037, 835, "Brown Lands");
        tboxes[41] = new TerritoryBox(1145, 885, "Rhun Hills");
        tboxes[42] = new TerritoryBox(285, 1285, "Druwaith Iaur");
        tboxes[43] = new TerritoryBox(225, 1455, "Andrast");
        tboxes[44] = new TerritoryBox(355, 1440, "Anfalas");
        tboxes[45] = new TerritoryBox(460, 1325, "Vale of Erech");
        tboxes[46] = new TerritoryBox(543, 1355, "Lamedon");
        tboxes[47] = new TerritoryBox(570, 1455, "Belfalas");
        tboxes[48] = new TerritoryBox(660, 1345, "Lebennin");
        tboxes[49] = new TerritoryBox(730, 1280, "Minas Tirith");
        tboxes[50] = new TerritoryBox(805, 1270, "Ithilien");
        tboxes[51] = new TerritoryBox(810, 1480, "South Ithilien");
        tboxes[52] = new TerritoryBox(905, 1270, "Udun Vale");
        tboxes[53] = new TerritoryBox(1140, 1290, "Barad-dur");
        tboxes[54] = new TerritoryBox(1095, 1430, "Gorgoroth");
        tboxes[55] = new TerritoryBox(1003, 1535, "Nurn");
        tboxes[56] = new TerritoryBox(930, 1400, "Minas Morgul");
        tboxes[57] = new TerritoryBox(740, 1590, "Harondor");
        tboxes[58] = new TerritoryBox(685, 1685, "Umbar");
        tboxes[59] = new TerritoryBox(580, 1800, "Deep Harad");
        tboxes[60] = new TerritoryBox(862, 1787, "Harad");
        tboxes[61] = new TerritoryBox(1045, 1750, "Near Harad");
        tboxes[62] = new TerritoryBox(1155, 1720, "Khand");
	}
		
}


