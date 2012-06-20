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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


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

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		
		final Button showVideo = new Button("Show Video");
		final DialogBox videoBox = new DialogBox();
		RootPanel.get("vid5").add(showVideo);
		
		
		videoBox.setText("Video Demo");
		videoBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		
		
		VideoWidget videoPlayer = new VideoWidget(false, true, "vid5demo/videos/Wildlife.jpg");
		
        List<VideoSource> sources = new ArrayList<VideoSource>();
        sources.add(new VideoSource("vid5demo/videos/Wildlife.webm", VideoType.WEBM));
        videoPlayer.setSources(sources);
        videoPlayer.setPixelSize(500, 400);
        
        dialogVPanel.add(videoPlayer);
		 
		
		dialogVPanel.add(closeButton);
		videoBox.setWidget(dialogVPanel);

		
		showVideo.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get("video").insert(videoBox, 0);
			}
		});
		
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get("video").clear();
			}
		});
		
		
	
	}
}
