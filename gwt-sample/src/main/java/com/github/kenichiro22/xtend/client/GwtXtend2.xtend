package com.github.kenichiro22.xtend.client

import com.github.kenichiro22.xtend.shared.FieldVerifier
import com.google.gwt.core.client.EntryPoint
import com.google.gwt.core.client.GWT
import com.google.gwt.event.dom.client.KeyCodes
import com.google.gwt.user.client.rpc.AsyncCallback
import com.google.gwt.user.client.ui.Button
import com.google.gwt.user.client.ui.DialogBox
import com.google.gwt.user.client.ui.HTML
import com.google.gwt.user.client.ui.Label
import com.google.gwt.user.client.ui.RootPanel
import com.google.gwt.user.client.ui.TextBox
import com.google.gwt.user.client.ui.VerticalPanel

import static com.github.kenichiro22.xtend.client.GwtXtend2.*

class GwtXtend2 implements EntryPoint, AsyncCallback<String> {
	/**
 	* The message displayed to the user when the server cannot be reached or
 	* returns an error.
 	*/
 	static val SERVER_ERROR = "An error occurred while 
      attempting to contact the server. Please check your network
      connection and try again."

 	/**
 	* Create a remote service proxy to talk to the server-side Greeting service.
 	*/
 	static val GreetingServiceAsync greetingService = GWT::create(typeof(GreetingService))

 	static val Messages messages = GWT::create(typeof(Messages))

	val errorLabel = new Label
  
  	val sendButton = new Button(messages.sendButton)
	val nameField = new TextBox
	
	val textToServerLabel = new Label
	val serverResponseLabel = new HTML
	
	val dialogBox = new DialogBox
	val closeButton = new Button("Close")
  
	/**
	 * This is the entry point method.
	 */
	override onModuleLoad() {
		nameField => [
			text = messages.nameField
			focus = true
		]	    
	    nameField.addKeyUpHandler [ event |
	        if (event.nativeKeyCode == KeyCodes::KEY_ENTER) {
	          sendNameToServer
	        }
	    ]

		// We can add style names to widgets
		sendButton.addStyleName("sendButton")
		sendButton.addClickHandler [
	    	sendNameToServer
	    ]
		
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
	    RootPanel::get("nameFieldContainer").add(nameField)
	    RootPanel::get("sendButtonContainer").add(sendButton)
	    RootPanel::get("errorLabelContainer").add(errorLabel)

	    // Focus the cursor on the name field when the app loads
	    nameField.selectAll()

	    // Create the popup dialog box
	    dialogBox => [
	    	text = "Remote Procedure Call"
	    	animationEnabled = true
	    ]
    
		// We can set the id of a widget by accessing its Element
	    closeButton => [
	    	element.id = "closeButton"
	    ]
	    // Add a handler to close the DialogBox
	    closeButton.addClickHandler [
	    	dialogBox.hide
	    	sendButton.enabled = true
	    	sendButton.focus = true
	    ]
       
	    
	    val dialogVPanel = new VerticalPanel
	    dialogVPanel.addStyleName("dialogVPanel")
	    dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"))
	    dialogVPanel.add(textToServerLabel)
	    dialogVPanel.add(new HTML("<br><b>Server replies:</b>"))
	    dialogVPanel.add(serverResponseLabel)
	    dialogVPanel.setHorizontalAlignment(VerticalPanel::ALIGN_RIGHT)
	    dialogVPanel.add(closeButton)
	    dialogBox.widget = dialogVPanel	    
	  }	

	      /**
	       * Send the name from the nameField to the server and wait for a response.
	       */
	      def void sendNameToServer() {
	        // First, we validate the input.
	        errorLabel.text = ""
	        val textToServer = nameField.text
	        if (!FieldVerifier::isValidName(textToServer)) {
	          errorLabel.setText("Please enter at least four characters")
	          return
	        }
	
	        // Then, we send the input to the server.
	        sendButton.setEnabled(false)
	        textToServerLabel.setText(textToServer)
	        serverResponseLabel.setText("")
	        greetingService.greetServer(textToServer, this)
	      }

	          override void onFailure(Throwable caught) {
	            // Show the RPC error message to the user
	            dialogBox.text = "Remote Procedure Call - Failure"
	            serverResponseLabel.addStyleName("serverResponseLabelError")
	            serverResponseLabel.HTML = SERVER_ERROR
	            dialogBox.center()
	            closeButton.focus = true
	          }
	
	          override void onSuccess(String result) {
	            dialogBox.text = "Remote Procedure Call"
	            serverResponseLabel.removeStyleName("serverResponseLabelError")
	            serverResponseLabel.HTML = result
	            dialogBox.center()
	            closeButton.focus = true
	          }

	    
	
}