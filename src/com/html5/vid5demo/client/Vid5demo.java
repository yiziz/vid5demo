package com.html5.vid5demo.client;

import java.util.ArrayList;
import java.util.List;

import com.html5.vid5demo.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;


import fr.hd3d.html5.video.client.VideoSource;
import fr.hd3d.html5.video.client.VideoWidget;
import fr.hd3d.html5.video.client.VideoSource.VideoType;
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Vid5demo implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	
	public static native void initVideoJS(String id) /*-{
	  $wnd._V_(id, {}, function(){
	  });
	}-*/;
	
	public static native void pauseVideoJS(String id) /*-{
	  $wnd._V_(id).pause();
	}-*/;
	
	public static native void alert(String msg) /*-{
	  $wnd.alert(msg);
	}-*/;
	
	public static native int offsetTop(String id) /*-{
	  return $wnd.$(id).offset().top;
	}-*/;
	
	
	public static native int offsetLeft(String id) /*-{
	  return $wnd.$(id).offset().left;
	}-*/;
	
	public static native int totalHeight(String id) /*-{
	  tHeight = $wnd.$(id).outerHeight(true);
	  return tHeight != null ? tHeight : -1;
	}-*/;
	
	public static native int totalWidth(String id) /*-{
	  tWidth = $wnd.$(id).outerWidth(true);
	  return tWidth != null ? tWidth : -1;
	}-*/;
	
	
	
	public static native int dummy(int num) /*-{
	  return num;
	}-*/;
	
	public enum Location {
		TOP, LEFT, RIGHT, BOTTOM,
	}
	
	public static int setVideoPos(String markerId, String vidId, Location Loc, Integer percentage) {
		
		if (percentage == null) {
			percentage = 50;
		} else if (percentage < 0) {
			percentage = 0;
		} else if (percentage > 100) {
			percentage = 100;
		}
		
		if (Loc == null){
			Loc = Location.TOP;
		}
		
		final Element video = DOM.getElementById(vidId);
		final Element marker = DOM.getElementById(markerId);
		NodeList<Element> children = video.getElementsByTagName("div") ;
		Element arrow = null;
		
		for (int i = 0; i < children.getLength(); i++) {
			Element temp = children.getItem(i);
			if (temp.getClassName().contains("vidArrow")) {
				arrow = temp;
				break;
			}
		}
		
		if (arrow == null) {
			return -1;
		}
		
		final Integer mHeight = marker.getOffsetHeight();
		final Integer mWidth = marker.getOffsetWidth();
		final Integer mTop = marker.getAbsoluteTop();
		final Integer mLeft = marker.getAbsoluteLeft();
		
		Integer vHeight = video.getOffsetHeight();
		Integer vWidth = video.getOffsetWidth();
		Integer aHeight = arrow.getOffsetHeight();
		Integer aWidth = arrow.getOffsetWidth();
		Integer aMoveHeight = vHeight - aHeight;
		Integer aMoveWidth = vWidth - aWidth;
		Integer gap = 2;
		
		Integer vLeft = mLeft;
		Integer vTop = mTop;
		Integer aLeft = 0;
		Integer aTop = 0;
		Integer shiftLeft = 0;
		Integer shiftDown = 0;
		
		
		switch(Loc) {
			case TOP:
				vLeft -= (vWidth/2 - mWidth/2);
				shiftLeft = (int)((float)(percentage)/100*aMoveWidth);
				vLeft -= (shiftLeft - vWidth/2 + aWidth/2);
			
				aLeft = shiftLeft;
			
				vTop -= (vHeight + gap);
				break;
			case LEFT:
				// TODO
				break;
			case BOTTOM:
				// TODO
				break;
			case RIGHT:
				// TODO
				break;
				
		}
		
		
		
		
		DOM.setStyleAttribute((com.google.gwt.user.client.Element) video, "position", "absolute");
		DOM.setStyleAttribute((com.google.gwt.user.client.Element) video, "top", vTop.toString()+"px");
		DOM.setStyleAttribute((com.google.gwt.user.client.Element) video, "left", vLeft.toString()+"px");
		
		DOM.setStyleAttribute((com.google.gwt.user.client.Element) arrow, "position", "relative");
		DOM.setStyleAttribute((com.google.gwt.user.client.Element) arrow, "top", aTop.toString()+"px");
		DOM.setStyleAttribute((com.google.gwt.user.client.Element) arrow, "left", aLeft.toString()+"px");
	
		
		
		return 0;
		
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		
		
		final Button showVideo = new Button("Show Video");
		RootPanel.get("vid5").add(showVideo);
		final RootPanel video = RootPanel.get("video");
		final Element arrow = DOM.getElementById("vidArrow");
		//RootPanel jsVid = RootPanel.get("my_video_1");
		DOM.setElementAttribute(showVideo.getElement(), "id", "showVideo");
		video.setVisible(false);
		
		
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		
		
		final VideoWidget videoPlayer = new VideoWidget(false, true, "vid5demo/videos/Wildlife.jpg");
		
        List<VideoSource> sources = new ArrayList<VideoSource>();
        sources.add(new VideoSource("vid5demo/videos/Wildlife.webm", VideoType.WEBM));
        videoPlayer.setSources(sources);
        videoPlayer.setStyleName("video-js vjs-default-skin");
        String vId = "my_video_1";
        DOM.setElementAttribute(videoPlayer.getElement(), "id", vId);
        DOM.setElementAttribute(videoPlayer.getElement(), "width", "320");
        DOM.setElementAttribute(videoPlayer.getElement(), "height", "132");
        video.insert(videoPlayer,0);
        initVideoJS(videoPlayer.getElement().getId());
        
        
        video.insert(closeButton,1);
		
		final Integer bHeight = showVideo.getOffsetHeight();
		final Integer bWidth = showVideo.getOffsetWidth();
		final Integer bTop = showVideo.getAbsoluteTop();
		final Integer bLeft = showVideo.getAbsoluteLeft();
		//Integer vHeight = totalHeight(video.getElement().getId());
		//alert(vHeight.toString());
		
		
		
		
		//video.insert(jsVid,0);
		showVideo.addMouseOverHandler(new MouseOverHandler(){
			
			public void onMouseOver(MouseOverEvent event) {
				// TODO Auto-generated method stub
				video.setVisible(true);
				
				setVideoPos(showVideo.getElement().getId(), video.getElement().getId(), null, null);
				

			}
		});
		
		showVideo.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//RootPanel.get("video").insert(videoPlayer, 0);
				video.setVisible(true);
			}
		});
		
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				video.setVisible(false);
				pauseVideoJS(videoPlayer.getElement().getId());
			}
		});
		
		
		
		
	
	}
	
	
	
	
	
	
}
