package com.gestion.logements;

public class Logement {
    private String adresse;
    private double superficie;
    private int nombrePieces;
    private Proprietaire proprietaire;
    private Quartier quartier;
    private Contrat contratActuel;

    public Logement(String adresse, double superficie, int nombrePieces) {
        this.adresse = adresse;
        this.superficie = superficie;
        this.nombrePieces = nombrePieces;
    }

    public boolean estLoue() { return contratActuel != null && contratActuel.estActif(); }
    public Locataire getLocataireActuel() { return estLoue() ? contratActuel.getLocataire() : null; }

    // Getters et Setters
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public double getSuperficie() { return superficie; }
    public void setSuperficie(double superficie) { this.superficie = superficie; }
    public int getNombrePieces() { return nombrePieces; }
    public void setNombrePieces(int nombrePieces) { this.nombrePieces = nombrePieces; }
    public Proprietaire getProprietaire() { return proprietaire; }
    public void setProprietaire(Proprietaire proprietaire) { this.proprietaire = proprietaire; }
    public Quartier getQuartier() { return quartier; }
    public void setQuartier(Quartier quartier) { this.quartier = quartier; }
    public Contrat getContratActuel() { return contratActuel; }
    public void setContratActuel(Contrat contratActuel) { this.contratActuel = contratActuel; }

    public void modifier(String adresse, Double superficie, Integer nombrePieces) {
        if (adresse != null && !adresse.isBlank()) this.adresse = adresse;
        if (superficie != null) this.superficie = superficie;
        if (nombrePieces != null) this.nombrePieces = nombrePieces;
    }

    @Override
    public String toString() {
        return adresse + " - " + nombrePieces + "p, " + superficie + "m² - " + (estLoue() ? "Loué" : "Libre");
    }
}