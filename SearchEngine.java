import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The ArrayList that will be searched through
    ArrayList<String> searches = new ArrayList<>();

    public String handleRequest(URI url) {
        System.out.println("Path: " + url.getPath());
        if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            if(parameters[0].equals("s")) {
                searches.add(parameters[1]);
                return String.format("Added %s to searches!", parameters[1]);
            }   
        }
        else if (url.getPath().contains("/search")) {
            String[] parameters = url.getQuery().split("=");
            if(parameters[0].equals("s")) {
                String searchQuery = parameters[1];
                String outputString = "Results: ";

                for (int i = 0; i < searches.size(); ++i) {
                    if (searches.get(i).contains(searchQuery)) {
                        outputString = outputString.concat(searches.get(i));
                        outputString = outputString.concat(" ");
                        //System.out.println(searches.get(i));
                    }
                }
                return String.format(outputString);
            }   
        }
        return "404 Not Found!";
    }
}

public class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}