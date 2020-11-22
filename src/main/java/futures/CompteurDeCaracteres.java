package futures;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.Callable;
import java.time.Instant;

public class CompteurDeCaracteres implements Callable<ResultatDuCompte> {

    private final String urlATraiter;

    public CompteurDeCaracteres(String currentUrl) {
        this.urlATraiter = currentUrl;
    }

    /**
     * Lit le contenu d'une URL, calcule le nombre d'octets et le temps écoulé
     * Décrémente le compte à rebours
     * @return ResultatDuCompte, contient le nombre d'octets et le temps écoulé
     * @throws IOException en cas d'erreur de lecture
     */
    @Override
    public ResultatDuCompte call() throws IOException {
        Instant start = Instant.now();

        int nombreDeCaracteres = 0;
        URL url = new URL(urlATraiter);

        // On lit le contenu de l'URL ligne par ligne
        try (LineNumberReader in =
                 new LineNumberReader(
                     new InputStreamReader(url.openStream())
                 )
        ) {
            String line;
            while ((line = in.readLine()) != null) {
                nombreDeCaracteres += line.length();
            }
        }

        System.out.printf("L'URL %s compte %d octets %n", urlATraiter, nombreDeCaracteres);
        Duration tempsDeCalcul = Duration.between(start, Instant.now());
        // On renvoie le résultat du calcul
        return new ResultatDuCompte(nombreDeCaracteres, tempsDeCalcul );
    }
}
