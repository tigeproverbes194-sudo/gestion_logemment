package com.gestion.logements;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Contrat {
    private Date dateDebut;
    private Date dateFin;
    private double prixMensuel;
    private Locataire locataire;
    private Proprietaire proprietaire;
    private Logement logement;

    public Contrat(Date dateDebut, Date dateFin, double prixMensuel,
                   Logement logement, Proprietaire proprietaire, Locataire locataire) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prixMensuel = prixMensuel;
        this.logement = logement;
        this.proprietaire = proprietaire;
        this.locataire = locataire;

        logement.setContratActuel(this);
        proprietaire.ajouterContrat(this);
        locataire.signerContrat(this);
    }

    public boolean estActif() {
        Date maintenant = new Date();
        return maintenant.after(dateDebut) && maintenant.before(dateFin);
    }

    public long getDureeEnMois() {
        long diff = dateFin.getTime() - dateDebut.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) / 30;
    }

    public double calculerMontantTotal() {
        return getDureeEnMois() * prixMensuel;
    }

    public void resilier() {
        // Libérer le logement
        if (this.logement.getContratActuel() == this)
            this.logement.setContratActuel(null);
        // Retirer des listes
        this.proprietaire.retirerContrat(this);
        this.locataire.retirerContrat(this);
    }

    // Getters et Setters
    public Date getDateDebut() { return dateDebut; }
    public void setDateDebut(Date dateDebut) { this.dateDebut = dateDebut; }
    public Date getDateFin() { return dateFin; }
    public void setDateFin(Date dateFin) { this.dateFin = dateFin; }
    public double getPrixMensuel() { return prixMensuel; }
    public void setPrixMensuel(double prixMensuel) { this.prixMensuel = prixMensuel; }
    public Locataire getLocataire() { return locataire; }
    public void setLocataire(Locataire locataire) { this.locataire = locataire; }
    public Proprietaire getProprietaire() { return proprietaire; }
    public void setProprietaire(Proprietaire proprietaire) { this.proprietaire = proprietaire; }
    public Logement getLogement() { return logement; }
    public void setLogement(Logement logement) { this.logement = logement; }

    @Override
    public String toString() {
        return "Contrat: " + logement.getAdresse() + " - " + locataire.getNomComplet() +
               " - " + prixMensuel + "€/mois (" + (estActif() ? "actif" : "inactif") + ")";
    }
}