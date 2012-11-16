package com.github.kenichiro22.xtend.client;

import com.github.kenichiro22.xtend.client.GreetingService;
import com.github.kenichiro22.xtend.client.GreetingServiceAsync;
import com.github.kenichiro22.xtend.client.Messages;
import com.github.kenichiro22.xtend.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class GwtXtend2 implements EntryPoint, AsyncCallback<String> {
  /**
   * The message displayed to the user when the server cannot be reached or
   * returns an error.
   */
  private final static String SERVER_ERROR = "An error occurred while \n      attempting to contact the server. Please check your network\n      connection and try again.";
  
  /**
   * Create a remote service proxy to talk to the server-side Greeting service.
   */
  private final static GreetingServiceAsync greetingService = new Function0<GreetingServiceAsync>() {
    public GreetingServiceAsync apply() {
      GreetingServiceAsync _create = GWT.<GreetingServiceAsync>create(GreetingService.class);
      return _create;
    }
  }.apply();
  
  private final static Messages messages = new Function0<Messages>() {
    public Messages apply() {
      Messages _create = GWT.<Messages>create(Messages.class);
      return _create;
    }
  }.apply();
  
  private final Label errorLabel = new Function0<Label>() {
    public Label apply() {
      Label _label = new Label();
      return _label;
    }
  }.apply();
  
  private final Button sendButton = new Function0<Button>() {
    public Button apply() {
      String _sendButton = GwtXtend2.messages.sendButton();
      Button _button = new Button(_sendButton);
      return _button;
    }
  }.apply();
  
  private final TextBox nameField = new Function0<TextBox>() {
    public TextBox apply() {
      TextBox _textBox = new TextBox();
      return _textBox;
    }
  }.apply();
  
  private final Label textToServerLabel = new Function0<Label>() {
    public Label apply() {
      Label _label = new Label();
      return _label;
    }
  }.apply();
  
  private final HTML serverResponseLabel = new Function0<HTML>() {
    public HTML apply() {
      HTML _hTML = new HTML();
      return _hTML;
    }
  }.apply();
  
  private final DialogBox dialogBox = new Function0<DialogBox>() {
    public DialogBox apply() {
      DialogBox _dialogBox = new DialogBox();
      return _dialogBox;
    }
  }.apply();
  
  private final Button closeButton = new Function0<Button>() {
    public Button apply() {
      Button _button = new Button("Close");
      return _button;
    }
  }.apply();
  
  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    final Procedure1<TextBox> _function = new Procedure1<TextBox>() {
        public void apply(final TextBox it) {
          String _nameField = GwtXtend2.messages.nameField();
          it.setText(_nameField);
          it.setFocus(true);
        }
      };
    ObjectExtensions.<TextBox>operator_doubleArrow(
      this.nameField, _function);
    final Procedure1<KeyUpEvent> _function_1 = new Procedure1<KeyUpEvent>() {
        public void apply(final KeyUpEvent event) {
          int _nativeKeyCode = event.getNativeKeyCode();
          boolean _equals = (_nativeKeyCode == KeyCodes.KEY_ENTER);
          if (_equals) {
            GwtXtend2.this.sendNameToServer();
          }
        }
      };
    this.nameField.addKeyUpHandler(new KeyUpHandler() {
        public void onKeyUp(KeyUpEvent event) {
          _function_1.apply(event);
        }
    });
    this.sendButton.addStyleName("sendButton");
    final Procedure1<ClickEvent> _function_2 = new Procedure1<ClickEvent>() {
        public void apply(final ClickEvent it) {
          GwtXtend2.this.sendNameToServer();
        }
      };
    this.sendButton.addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent event) {
          _function_2.apply(event);
        }
    });
    RootPanel _get = RootPanel.get("nameFieldContainer");
    _get.add(this.nameField);
    RootPanel _get_1 = RootPanel.get("sendButtonContainer");
    _get_1.add(this.sendButton);
    RootPanel _get_2 = RootPanel.get("errorLabelContainer");
    _get_2.add(this.errorLabel);
    this.nameField.selectAll();
    final Procedure1<DialogBox> _function_3 = new Procedure1<DialogBox>() {
        public void apply(final DialogBox it) {
          it.setText("Remote Procedure Call");
          it.setAnimationEnabled(true);
        }
      };
    ObjectExtensions.<DialogBox>operator_doubleArrow(
      this.dialogBox, _function_3);
    final Procedure1<Button> _function_4 = new Procedure1<Button>() {
        public void apply(final Button it) {
          Element _element = it.getElement();
          _element.setId("closeButton");
        }
      };
    ObjectExtensions.<Button>operator_doubleArrow(
      this.closeButton, _function_4);
    final Procedure1<ClickEvent> _function_5 = new Procedure1<ClickEvent>() {
        public void apply(final ClickEvent it) {
          GwtXtend2.this.dialogBox.hide();
          GwtXtend2.this.sendButton.setEnabled(true);
          GwtXtend2.this.sendButton.setFocus(true);
        }
      };
    this.closeButton.addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent event) {
          _function_5.apply(event);
        }
    });
    VerticalPanel _verticalPanel = new VerticalPanel();
    final VerticalPanel dialogVPanel = _verticalPanel;
    dialogVPanel.addStyleName("dialogVPanel");
    HTML _hTML = new HTML("<b>Sending name to the server:</b>");
    dialogVPanel.add(_hTML);
    dialogVPanel.add(this.textToServerLabel);
    HTML _hTML_1 = new HTML("<br><b>Server replies:</b>");
    dialogVPanel.add(_hTML_1);
    dialogVPanel.add(this.serverResponseLabel);
    dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
    dialogVPanel.add(this.closeButton);
    this.dialogBox.setWidget(dialogVPanel);
  }
  
  /**
   * Send the name from the nameField to the server and wait for a response.
   */
  public void sendNameToServer() {
    this.errorLabel.setText("");
    final String textToServer = this.nameField.getText();
    boolean _isValidName = FieldVerifier.isValidName(textToServer);
    boolean _not = (!_isValidName);
    if (_not) {
      this.errorLabel.setText("Please enter at least four characters");
      return;
    }
    this.sendButton.setEnabled(false);
    this.textToServerLabel.setText(textToServer);
    this.serverResponseLabel.setText("");
    GwtXtend2.greetingService.greetServer(textToServer, this);
  }
  
  public void onFailure(final Throwable caught) {
    this.dialogBox.setText("Remote Procedure Call - Failure");
    this.serverResponseLabel.addStyleName("serverResponseLabelError");
    this.serverResponseLabel.setHTML(GwtXtend2.SERVER_ERROR);
    this.dialogBox.center();
    this.closeButton.setFocus(true);
  }
  
  public void onSuccess(final String result) {
    this.dialogBox.setText("Remote Procedure Call");
    this.serverResponseLabel.removeStyleName("serverResponseLabelError");
    this.serverResponseLabel.setHTML(result);
    this.dialogBox.center();
    this.closeButton.setFocus(true);
  }
}
