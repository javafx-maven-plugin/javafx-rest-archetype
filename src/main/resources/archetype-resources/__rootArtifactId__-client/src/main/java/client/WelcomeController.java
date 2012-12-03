#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.client;

import ${package}.common.WelcomeMessage;
import javafx.beans.binding.BooleanBinding;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class WelcomeController {

    private static final Logger log = LoggerFactory.getLogger(WelcomeController.class);

    @FXML private Parent root;
    @FXML private TextField nameField;
    @FXML private Button sayHelloButton;
    @FXML private TextArea messageArea;
    @FXML private ProgressIndicator loadingIndicator;

    @Autowired
    private WelcomeRestService welcomeRestService;

    public Parent getRoot() {
        return root;
    }

    public void sayHello() {

        final String name = nameField.getText();
        Task<WelcomeMessage> task = new Task<WelcomeMessage>() {
            @Override
            protected WelcomeMessage call() throws Exception {
                // simulate a slow server with: try {Thread.sleep(2000);} catch (Exception e) {}
                return welcomeRestService.sayHello(name);
            }

            @Override
            protected void succeeded() {
                WelcomeMessage message = getValue();
                log.debug("Server returned success: " + message);
                messageArea.appendText("Sever says: " + message.getMessage() + "${symbol_escape}n");
            }

            @Override
            protected void failed() {
                Throwable exception = getException();
                log.error("Server returned error: " + exception, exception);
                messageArea.setText("Something bad happened: " + exception.getMessage() + "${symbol_escape}n");
            }
        };

        BooleanBinding runningBinding = task.stateProperty().isEqualTo(Task.State.RUNNING);
        nameField.disableProperty().bind(runningBinding);
        sayHelloButton.disableProperty().bind(runningBinding);
        loadingIndicator.visibleProperty().bind(runningBinding);

        new Thread(task).start();
    }
}
