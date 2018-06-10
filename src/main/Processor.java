import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDF;

import java.util.Iterator;

public class Processor {
    private int something = 0;
    private int numSubjects = 0;
    private int numNameSpaces = 0;

    public static void main(String[] args) {
        if (args.length != 1) {
            new Processor().process("http://dbpedia.org/data/Linz.rdf");
        } else {
            new Processor().process(args[0]);
        }
    }

    private void process(String uri) {
//        Create Model and load RDF data using URI
        Model model = ModelFactory.createDefaultModel();
        model.read(uri);

        System.out.println("Graph size: " + model.getGraph().size());

//    Calculate and print the total number of subjects
        calculate(model.listSubjects(), numSubjects, "Number of subjects: ");

//    Calculate and print the total number of namespaces in use
        calculate(model.listNameSpaces(), numNameSpaces, "Number of namespaces: ");

        calculateSomething(model);
    }

    private void calculateSomething(Model model) {
        model.listStatements(new SimpleSelector(null, RDF.type, (RDFNode)null));
        ResIterator iterator = model.listSubjectsWithProperty(model.getProperty("http://dbpedia.org/ontology/abstract"));
//        ResIterator iterator = model.listSubjectsWithProperty(model.getProperty("http://dbpedia.org/ontology/abstract"));
        while (iterator.hasNext()) {
            Resource r = iterator.next();
            something++;
        }
        System.out.println("Number of birthplace listings: " + something);
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