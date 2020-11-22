
package futures;

import java.time.Duration;

/**
 * Le résultat du calcul de CompteurDeCaracteres
 **/
public class ResultatDuCompte {
        // Le nombre d'octets dans le résultat
	public final int nombreDeCaracteres;
        // Combien de temps ça a pris pour calculer le résultat
	public final Duration tempsDeCalcul;
	public ResultatDuCompte(int nombre, Duration temps) {
		nombreDeCaracteres  = nombre;
		tempsDeCalcul = temps;
	}
}
