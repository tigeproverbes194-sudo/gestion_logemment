package com.gestion.logements;

import java.util.ArrayList;
import java.util.List;

public class Proprietaire extends Individu {
    private List<Logement> logements;
    private List<Contrat> contrats;

    public Proprietaire(String nom, String prenom, String telephone, String email) {
        super(nom, prenom, telephone, email);
        this.logements = new ArrayList<>();
        this.contrats = new ArrayList<>();
    }

    public void ajouterLogement(Logement logement) {
        logements.add(logement);
        logement.setProprietaire(this);
    }

    public boolean retirerLogement(Logement logement) {
        if (logement.estLoue()) return false; // Ne pas retirer un logement loué
        logements.remove(logement);
        if (logement.getQuartier() != null)
            logement.getQuartier().getLogements().remove(logement);
        logement.setProprietaire(null);
        return true;
    }

    public List<Logement> getLogements() { return logements; }
    public List<Contrat> getContrats() { return contrats; }
    public void ajouterContrat(Contrat contrat) { contrats.add(contrat); }
    public boolean retirerContrat(Contrat contrat) { return contrats.remove(contrat); }

    public List<Locataire> getLocataires() {
        List<Locataire> result = new ArrayList<>();
        for (Contrat c : contrats)
            if (c.estActif())
                result.add(c.getLocataire());
        return result;
    }

    public void afficherLocataires() {
        System.out.println("Locataires du propriétaire " + getNomComplet() + ":");
        List<Locataire> locs = getLocataires();
        if (locs.isEmpty()) System.out.println("  Aucun");
        else for (Locataire l : locs) System.out.println("  - " + l);
    }

    public void modifier(String nom, String prenom, String telephone, String email) {
        if (nom != null && !nom.isBlank()) setNom(nom);
        if (prenom != null && !prenom.isBlank()) setPrenom(prenom);
        if (telephone != null && !telephone.isBlank()) setTelephone(telephone);
        if (email != null && !email.isBlank()) setEmail(email);
    }

    @Override
    public String toString() {
        return "Propriétaire: " + getNomComplet();
    }
}