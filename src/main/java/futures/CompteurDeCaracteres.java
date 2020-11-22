package futures;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.Callable;
import java.time.Instant;
import java.util.Scanner;

public class CompteurDeCaracteres implements Callable<ResultatDuCompte> {

    private final String urlATraiter;

    public CompteurDeCaracteres(String currentUrl) {
        this.urlATraiter = currentUrl;
    }

    /**
     * Lit le contenu d'une URL, calcule le nombre d'octets et le temps écoulé
     * @return ResultatDuCompte, contient le nombre d'octets et le temps écoulé
     * @throws IOException en cas d'erreur de lecture
     */
    @Override
    public ResultatDuCompte call() throws IOException {
        Instant start = Instant.now();

        // Formule magique pour lire le contenu d'un URL dans une chaine de caractères
        String out = new Scanner(new URL(urlATraiter).openStream(), "UTF-8").useDelimiter("\\A").next();
        
        int nombreDeCaracteres = out.length();
        Duration tempsDeCalcul = Duration.between(start, Instant.now());

        System.out.printf("Il y a %d caractères dans l'URL %s (%s) %n", nombreDeCaracteres, urlATraiter, tempsDeCalcul);
        // On renvoie le résultat du calcul
        return new ResultatDuCompte(nombreDeCaracteres, tempsDeCalcul );
    }
}
