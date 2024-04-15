/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package jobs4u.base.app.common.console;

import eapli.framework.infrastructure.pubsub.PubSubRegistry;
import jobs4u.base.Application;
import eapli.framework.infrastructure.pubsub.EventDispatcher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Paulo Gandra Sousa
 */
@SuppressWarnings("squid:S106")
public abstract class BaseApplication {

    // we are assuming we will always use the in process event
    // dispatcher. check the Spring version of the Base project
    // for an alternative
    final EventDispatcher dispatcher = InProcessPubSub.dispatcher();

    protected static final String SEPARATOR_HR = "=====================================";
    private static final Logger LOGGER = LogManager.getLogger(BaseApplication.class);
    private static final ExecutorService executor = Executors.newFixedThreadPool(10); // ajuste o número de threads conforme necessário


    /**
     * @param args the command line arguments
     */
    public void run(final String[] args) {
        configureAuthz();
        configurePubSub();
        printHeader();

        try {
            setupEventHandlers();
            doMain(args); // submeta a tarefa para o executor

            printFooter();
        } catch (final Exception e) {
            System.out.println(
                    "Something unexpected has happened and the application will terminate. Please check the logs.\n");
            LOGGER.error(e);
        } finally {
            clearEventHandlers();
        }


        //TODO this code is not a good practice just here to test the create client and user, to change
        try {
            // Pause for 4 seconds
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            // This part is executed when an exception (in this case InterruptedException) occurs
            System.out.println("Sleep was interrupted");
        }

        // exiting the application, closing all threads
        System.exit(0);
    }


    protected void printFooter() {
        System.out.println("\n");
        System.out.println(SEPARATOR_HR);
        System.out.println(appGoodbye());
        System.out.println(SEPARATOR_HR);
    }

    protected void printHeader() {
        System.out.println(SEPARATOR_HR);
        System.out.println(appTitle() + " " + Application.VERSION);
        System.out.println(Application.COPYRIGHT);
        System.out.println(SEPARATOR_HR);
    }

    private final void clearEventHandlers() {
        try {
            doClearEventHandlers(dispatcher);

            PubSubRegistry.dispatcher().shutdown();
        } catch (final Exception e) {
            LOGGER.error("Unable to cleanup event handlers", e);
        }
    }

    private final void setupEventHandlers() {
        doSetupEventHandlers(dispatcher);
    }

    protected abstract void doMain(final String[] args);

    protected abstract String appTitle();

    protected abstract String appGoodbye();

    protected void doClearEventHandlers(final EventDispatcher dispatcher) {
        // nothing to do
    }

    protected void configurePubSub() {
        // TODO use a factory/registry to obtain the pub/sub engine
        /*
         * SimplePersistentPubSub.configure(PersistenceContext.repositories().
         * eventRecord(), PersistenceContext.repositories().eventConsumption(),
         * Application.settings().getProperty("eapli.framework.pubsub.instanceKey"),
         * Integer.valueOf(Application.settings().getProperty(
         * "eapli.framework.pubsub.poolInterval") ));
         * PubSubRegistry.configure(SimplePersistentPubSub.dispatcher(),
         * SimplePersistentPubSub.publisher());
         */
        PubSubRegistry.configure(InProcessPubSub.dispatcher(), InProcessPubSub.publisher());
    }

    protected abstract void doSetupEventHandlers(EventDispatcher dispatcher);

    protected abstract void configureAuthz();


}
