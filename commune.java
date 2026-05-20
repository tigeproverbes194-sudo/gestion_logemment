import java.util.ArrayList;
import java.util.List;

public class Commune {

    private String nom;
    private List<String> quartiers;
    private List<String> habitants;

    public Commune(String nom) {
        this.nom = nom;
        this.quartiers = new ArrayList<>();
        this.habitants = new ArrayList<>();
    }

    public void ajouterQuartier(String quartier) {
        quartiers.add(quartier);
    }

    public void ajouterHabitant(String habitant) {
        habitants.add(habitant);
    }

    public List<String> getHabitants() {
        return habitants;
    }

    public List<String> getQuartiers() {
        return quartiers;
    }

    public String getNom() {
        return nom;
    }

    public static void main(String[] args) {
        Commune commune = new Commune("Commune Centrale");
        commune.ajouterQuartier("Quartier Nord");
        commune.ajouterHabitant("Jean Dupont");
        System.out.println("Commune : " + commune.getNom());
        System.out.println("Quartiers : " + commune.getQuartiers());
        System.out.println("Habitants : " + commune.getHabitants());
    }
}
