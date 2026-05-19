import java.util.ArrayList;
import java.util.List;

public class Commune {
    
    private String nom;
    private List<Quartier> quartiers;

    public Commune(String nom) {
        this.nom = nom;
        this.quartiers = new ArrayList<>();
    }

    public void ajouterQuartier(Quartier q) {
        quartiers.add(q);
    }

    public List<Quartier> getQuartiers() {
        return quartiers;
    }

    public String getNom() {
        return nom;
    }

    public static void main(String[] args) {
        Commune commune = new Commune("Commune Centrale");
        System.out.println("Commune : " + commune.getNom());
    }
}
