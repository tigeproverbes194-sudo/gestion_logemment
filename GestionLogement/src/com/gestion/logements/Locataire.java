package com.gestion.logements;

import java.util.ArrayList;
import java.util.List;

public class Locataire extends Individu {
    private String pieceIdentite;
    private List<Contrat> contrats;

    public Locataire(String nom, String prenom, String telephone, String email, String pieceIdentite) {
        super(nom, prenom, telephone, email);
        this.pieceIdentite = pieceIdentite;
        this.contrats = new ArrayList<>();
    }

    public void signerContrat(Contrat contrat) {
        contrats.add(contrat);
        contrat.setLocataire(this);
    }

    public boolean retirerContrat(Contrat contrat) { return contrats.remove(contrat); }
    public List<Contrat> getContrats() { return contrats; }
    public String getPieceIdentite() { return pieceIdentite; }
    public void setPieceIdentite(String pieceIdentite) { this.pieceIdentite = pieceIdentite; }

    public Logement getLogementActuel() {
        for (Contrat c : contrats)
            if (c.estActif())
                return c.getLogement();
        return null;
    }

    public void modifier(String nom, String prenom, String telephone, String email, String pieceIdentite) {
        if (nom != null && !nom.isBlank()) setNom(nom);
        if (prenom != null && !prenom.isBlank()) setPrenom(prenom);
        if (telephone != null && !telephone.isBlank()) setTelephone(telephone);
        if (email != null && !email.isBlank()) setEmail(email);
        if (pieceIdentite != null && !pieceIdentite.isBlank()) this.pieceIdentite = pieceIdentite;
    }

    @Override
    public String toString() {
        return super.toString() + " - ID: " + pieceIdentite;
    }
}