package com.gestion.logements;

import java.util.ArrayList;
import java.util.List;

public class Quartier {
    private String nom;
    private String codeQuartier;
    private Commune commune;
    private List<Logement> logements;

    public Quartier(String nom, String codeQuartier) {
        this.nom = nom;
        this.codeQuartier = codeQuartier;
        this.logements = new ArrayList<>();
    }

    public void ajouterLogement(Logement logement) {
        logements.add(logement);
        logement.setQuartier(this);
    }

    public boolean retirerLogement(Logement logement) {
        if (logement.estLoue()) return false;
        logements.remove(logement);
        logement.setQuartier(null);
        return true;
    }

    public List<Logement> getLogements() { return logements; }
    public List<Locataire> getHabitants() {
        List<Locataire> habitants = new ArrayList<>();
        for (Logement l : logements)
            if (l.estLoue())
                habitants.add(l.getLocataireActuel());
        return habitants;
    }

    // Getters/Setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getCodeQuartier() { return codeQuartier; }
    public void setCodeQuartier(String codeQuartier) { this.codeQuartier = codeQuartier; }
    public Commune getCommune() { return commune; }
    public void setCommune(Commune commune) { this.commune = commune; }

    public void modifier(String nom, String code) {
        if (nom != null && !nom.isBlank()) this.nom = nom;
        if (code != null && !code.isBlank()) this.codeQuartier = code;
    }

    public void afficherHabitants() {
        System.out.println("Habitants du quartier " + nom + ":");
        List<Locataire> habitants = getHabitants();
        if (habitants.isEmpty()) System.out.println("  Aucun");
        else for (Locataire l : habitants) System.out.println("  - " + l);
    }

    @Override
    public String toString() {
        return "Quartier " + nom + " (" + codeQuartier + ")";
    }
}