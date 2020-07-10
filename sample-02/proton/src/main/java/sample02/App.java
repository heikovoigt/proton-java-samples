package sample02;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import com.hcl.domino.db.model.BulkOperationException;
import com.hcl.domino.db.model.Database;
import com.hcl.domino.db.model.Document;
import com.hcl.domino.db.model.Item;
import com.hcl.domino.db.model.OptionalItemNames;
import com.hcl.domino.db.model.Server;
/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        System.out.println("Hello World!");
        final String host = "<your proton hostname goes here>";
        final int port = 3002;
        final String clientCert = "<your client certficate goes here *.crt>";
        final String clientKey = "<your client key goes here *.key>";
        final String trustedRoots = "<your ca certificate goes here ca.crt>";
        final String keyPassword = null;
        final String idFilePassword = null;
        final String database = "<your nsf goes here>";
		final String query = "<your quer goes here>";
		
		try {
			Server server = new Server(
                    host, 
                    port, 
                    new File(trustedRoots),
                    new File(clientCert),
                    new File(clientKey), 
                    keyPassword, 
                    idFilePassword, 
                    Executors.newSingleThreadExecutor());
			Database db = server.useDatabase(database);
			List<String> items = new ArrayList<String>();
            items.add("Author");
			items.add("Subject");
			// Wait for computation to complete and then recieve result
			List<Document> docs = db.readDocuments(query, new OptionalItemNames(items)).get();
			
			for( Document doc : docs ) {
                List<Item<?>> author = doc.getItemByName("Author");
                List<Item<?>> subject = doc.getItemByName("Subject");
                System.out.println(author.get(0).getValue().get(0) + " " + subject.get(0).getValue().get(0));
            }


		} catch (IOException e) {
            
            e.printStackTrace();
        } catch (BulkOperationException e) {
            
            e.printStackTrace();
        } catch (InterruptedException e) {
            
            e.printStackTrace();
        } catch (ExecutionException e) {
            
            e.printStackTrace();
        }


    }
}
