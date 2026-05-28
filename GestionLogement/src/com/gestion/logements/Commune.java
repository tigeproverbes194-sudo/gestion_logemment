package com.gestion.logements;

import java.util.ArrayList;
import java.util.List;

public class Commune {
    private String nom;
    private String codePostal;
    private List<Quartier> quartiers;

    public Commune(String nom, String codePostal) {
        this.nom = nom;
        this.codePostal = codePostal;
        this.quartiers = new ArrayList<>();
    }

    public void ajouterQuartier(Quartier quartier) {
        quartiers.add(quartier);
        quartier.setCommune(this);
    }

    public boolean retirerQuartier(Quartier quartier) {
        // Vérifier si tous les logements du quartier sont libres
        for (Logement l : quartier.getLogements())
            if (l.estLoue()) return false;
        quartiers.remove(quartier);
        quartier.setCommune(null);
        return true;
    }

    public List<Quartier> getQuartiers() { return quartiers; }
    public List<Locataire> getHabitants() {
        List<Locataire> habitants = new ArrayList<>();
        for (Quartier q : quartiers)
            habitants.addAll(q.getHabitants());
        return habitants;
    }

    public List<Locataire> getHabitantsParQuartier(Quartier quartier) {
        if (quartiers.contains(quartier))
            return quartier.getHabitants();
        return new ArrayList<>();
    }

    public void modifier(String nom, String codePostal) {
        if (nom != null && !nom.isBlank()) this.nom = nom;
        if (codePostal != null && !codePostal.isBlank()) this.codePostal = codePostal;
    }

    public void afficherHabitants() {
        System.out.println("\n=== HABITANTS DE " + nom.toUpperCase() + " ===");
        List<Locataire> habitants = getHabitants();
        if (habitants.isEmpty()) System.out.println("Aucun habitant.");
        else for (Locataire l : habitants) System.out.println("  - " + l);
    }

    public void afficherHabitantsParQuartier() {
        System.out.println("\n=== HABITANTS PAR QUARTIER ===");
        for (Quartier q : quartiers)
            q.afficherHabitants();
    }

    // Getters
    public String getNom() { return nom; }
    public String getCodePostal() { return codePostal; }

    @Override
    public String toString() {
        return "Commune de " + nom + " (" + codePostal + ") - " + quartiers.size() + " quartiers";
    }
}