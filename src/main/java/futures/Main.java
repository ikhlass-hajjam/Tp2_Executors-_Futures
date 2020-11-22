package futures;


import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import futures.*;
import java.time.Duration;
import java.time.Instant;

public class Main {

    public static final int HOWMANYTHREADS = 4;

    public static void main(String[] args)
            throws InterruptedException, ExecutionException {
        // La liste des tâches à exécuter
        List<CompteurDeCaracteres> tachesAExecuter = List.of(
                new CompteurDeCaracteres("http://www.java2jee.blogspot.com"),
                new CompteurDeCaracteres("http://www.univ-jfc.fr"),
                new CompteurDeCaracteres("https://www.irit.fr/"),
                new CompteurDeCaracteres("http://www.google.fr"),
                new CompteurDeCaracteres("http://apache.org/"),
                new CompteurDeCaracteres("https://sourceforge.net")
         );
        // On crée l'exécuteur en précisant son nombre max de threads
        final ExecutorService executor = Executors.newFixedThreadPool(HOWMANYTHREADS);

        try {
            Instant start = Instant.now(); // Pour mesurer le temps effectif de calcul

            // On soumet la liste des tâches à exécuter à l'exécuteur,
            // qui renvoie une liste de résultats futurs
            List<Future<ResultatDuCompte>> resultatsFuturs = executor.invokeAll(tachesAExecuter);
                
            long nombreTotalDeCaracteres = 0;
            Duration sommeDestempsIndividuels = Duration.ZERO;
            // On parcourt les résultats pour additionner les temps individuels d'exécution            
            for (Future<ResultatDuCompte> futurResultat : resultatsFuturs) {
                ResultatDuCompte resultat = futurResultat.get(); // On bloquera sur la tâche la plus lente
                nombreTotalDeCaracteres += resultat.nombreDeCaracteres;
                sommeDestempsIndividuels  = sommeDestempsIndividuels.plus(resultat.tempsDeCalcul);
            }
            
            System.out.printf("Nombre total d'octets : %d %n", nombreTotalDeCaracteres);
            System.out.printf("Temps effectif de calcul ~ %d secondes %n", Duration.between(start, Instant.now()).toSeconds());
            System.out.printf("Somme des temps individuels ~ %d secondes %n", sommeDestempsIndividuels.toSeconds());
            
        } finally {
            executor.shutdown();
        }
    }
}
