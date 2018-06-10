import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDF;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

public class Processor {

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Please provide a valid RDF URL (e.g. http://dbpedia.org/data/Nintendo.rdf ): ");
            new Processor().process(br.readLine());
        } catch (Exception e) {
            System.out.println("Invalid input; using default: http://dbpedia.org/data/Linz.rdf");
            new Processor().process("http://dbpedia.org/data/Linz.rdf");
        }
    }

    private void process(String uri) {
//        Create Model and load RDF data using URI
        Model model = ModelFactory.createDefaultModel();
        model.read(uri);

        System.out.println("Graph size: " + model.getGraph().size());

//    Calculate and print the total number of subjects
        int numSubjects = 0;
        calculate(model.listSubjects(), numSubjects, "Number of subjects: ");

//    Calculate and print the total number of namespaces in use
        int numNameSpaces = 0;
        calculate(model.listNameSpaces(), numNameSpaces, "Number of namespaces: ");

//        Calculate and print the total number of statements with rdf.type predicate
        int numTypePredicates = 0;
        calculate(model.listStatements(
                new SimpleSelector(null, RDF.type, (RDFNode)null)),
                numTypePredicates,
                "Number of statements with rdf:type predicate: ");
    }

//    Counts the number of iterator items
    private void calculate(Iterator iterator, int counter, String message) {
        while (iterator.hasNext()) {
            iterator.next();
            counter++;
        }
        System.out.println(message + counter);
    }
}