package com.vaadin_lastfmclient.ui.main;

import javax.servlet.annotation.WebServlet;

import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin_lastfmclient.ui.components.MyUIBuilder;

@Title("Last FM Vaadin Client")
@SuppressWarnings("serial")
@Theme("vaadin_lastfmclient")
public class VaadinStartGUI extends UI 
{

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = VaadinStartGUI.class)
	public static class Servlet extends VaadinServlet 
	{
	
	}

	@Override
	protected void init(VaadinRequest request) 
	{
		HorizontalLayout mainVerticalLayout = new HorizontalLayout();
		MyUIBuilder myuibuilder = new MyUIBuilder();
		Label label = new Label("Artist Albums application");
		
		final  com.vaadin.ui.HorizontalSplitPanel horizontalFinalLayout = myuibuilder.returnLayout();
		
		mainVerticalLayout.addComponent(label);
		mainVerticalLayout.addComponent(horizontalFinalLayout);
		
		setContent(horizontalFinalLayout);	
	}

}