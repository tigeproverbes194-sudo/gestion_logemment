package com.gestion.logements;

import java.text.SimpleDateFormat;
import java.util.*;

public class GestionLogementsApp {

    private static List<Commune> communes = new ArrayList<>();
    private static List<Proprietaire> proprietaires = new ArrayList<>();
    private static List<Locataire> locataires = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choix;
        do {
            afficherMenuPrincipal();
            choix = lireEntier("Votre choix : ");
            switch (choix) {
                case 1 -> menuGestionCommunes();
                case 2 -> menuGestionQuartiers();
                case 3 -> menuGestionProprietaires();
                case 4 -> menuGestionLogements();
                case 5 -> menuGestionLocataires();
                case 6 -> menuGestionContrats();
                case 7 -> consulterHabitants();
                case 0 -> System.out.println("Au revoir !");
                default -> System.out.println("Choix invalide.");
            }
        } while (choix != 0);
        scanner.close();
    }

    private static void afficherMenuPrincipal() {
        System.out.println("\n=== GESTION DES LOGEMENTS (CRUD complet) ===");
        System.out.println("1. Gérer les communes");
        System.out.println("2. Gérer les quartiers");
        System.out.println("3. Gérer les propriétaires");
        System.out.println("4. Gérer les logements");
        System.out.println("5. Gérer les locataires");
        System.out.println("6. Gérer les contrats");
        System.out.println("7. Consulter les habitants (commune/quartier)");
        System.out.println("0. Quitter");
    }

    // ---------- Gestion des communes ----------
    private static void menuGestionCommunes() {
        int choix;
        do {
            System.out.println("\n--- Gestion des communes ---");
            System.out.println("1. Ajouter une commune");
            System.out.println("2. Modifier une commune");
            System.out.println("3. Supprimer une commune");
            System.out.println("4. Lister toutes les communes");
            System.out.println("0. Retour");
            choix = lireEntier("Choix : ");
            switch (choix) {
                case 1 -> ajouterCommune();
                case 2 -> modifierCommune();
                case 3 -> supprimerCommune();
                case 4 -> listerCommunes();
            }
        } while (choix != 0);
    }

    private static void ajouterCommune() {
        String nom = lireChaine("Nom de la commune : ");
        String cp = lireChaine("Code postal : ");
        communes.add(new Commune(nom, cp));
        System.out.println("Commune ajoutée.");
    }

    private static void modifierCommune() {
        if (communes.isEmpty()) { System.out.println("Aucune commune."); return; }
        Commune c = choisirCommune();
        if (c == null) return;
        String nom = lireChaine("Nouveau nom (vide pour inchangé) : ");
        String cp = lireChaine("Nouveau code postal : ");
        c.modifier(nom, cp);
        System.out.println("Commune modifiée.");
    }

    private static void supprimerCommune() {
        if (communes.isEmpty()) { System.out.println("Aucune commune."); return; }
        Commune c = choisirCommune();
        if (c == null) return;
        // Vérifier que tous les quartiers sont supprimables (aucun logement loué)
        for (Quartier q : c.getQuartiers()) {
            for (Logement l : q.getLogements()) {
                if (l.estLoue()) {
                    System.out.println("Impossible : un logement est encore loué dans le quartier " + q.getNom());
                    return;
                }
            }
        }
        communes.remove(c);
        System.out.println("Commune supprimée.");
    }

    private static void listerCommunes() {
        if (communes.isEmpty()) System.out.println("Aucune commune.");
        else for (Commune c : communes) System.out.println(c);
    }

    // ---------- Gestion des quartiers ----------
    private static void menuGestionQuartiers() {
        int choix;
        do {
            System.out.println("\n--- Gestion des quartiers ---");
            System.out.println("1. Ajouter un quartier");
            System.out.println("2. Modifier un quartier");
            System.out.println("3. Supprimer un quartier");
            System.out.println("4. Lister les quartiers d'une commune");
            System.out.println("0. Retour");
            choix = lireEntier("Choix : ");
            switch (choix) {
                case 1 -> ajouterQuartier();
                case 2 -> modifierQuartier();
                case 3 -> supprimerQuartier();
                case 4 -> listerQuartiers();
            }
        } while (choix != 0);
    }

    private static void ajouterQuartier() {
        if (communes.isEmpty()) { System.out.println("Aucune commune."); return; }
        Commune c = choisirCommune();
        if (c == null) return;
        String nom = lireChaine("Nom du quartier : ");
        String code = lireChaine("Code quartier : ");
        c.ajouterQuartier(new Quartier(nom, code));
        System.out.println("Quartier ajouté.");
    }

    private static void modifierQuartier() {
        if (communes.isEmpty()) { System.out.println("Aucune commune."); return; }
        Commune c = choisirCommune();
        if (c == null || c.getQuartiers().isEmpty()) { System.out.println("Aucun quartier."); return; }
        Quartier q = choisirQuartier(c);
        if (q == null) return;
        String nom = lireChaine("Nouveau nom : ");
        String code = lireChaine("Nouveau code : ");
        q.modifier(nom, code);
        System.out.println("Quartier modifié.");
    }

    private static void supprimerQuartier() {
        if (communes.isEmpty()) { System.out.println("Aucune commune."); return; }
        Commune c = choisirCommune();
        if (c == null || c.getQuartiers().isEmpty()) { System.out.println("Aucun quartier."); return; }
        Quartier q = choisirQuartier(c);
        if (q == null) return;
        // Vérifier que tous les logements du quartier sont libres
        for (Logement l : q.getLogements()) {
            if (l.estLoue()) {
                System.out.println("Impossible : un logement est encore loué dans ce quartier.");
                return;
            }
        }
        c.retirerQuartier(q);
        System.out.println("Quartier supprimé.");
    }

    private static void listerQuartiers() {
        if (communes.isEmpty()) { System.out.println("Aucune commune."); return; }
        Commune c = choisirCommune();
        if (c == null) return;
        if (c.getQuartiers().isEmpty()) System.out.println("Aucun quartier.");
        else for (Quartier q : c.getQuartiers()) System.out.println(q);
    }

    // ---------- Gestion des propriétaires ----------
    private static void menuGestionProprietaires() {
        int choix;
        do {
            System.out.println("\n--- Gestion des propriétaires ---");
            System.out.println("1. Ajouter un propriétaire");
            System.out.println("2. Modifier un propriétaire");
            System.out.println("3. Supprimer un propriétaire");
            System.out.println("4. Lister les propriétaires");
            System.out.println("0. Retour");
            choix = lireEntier("Choix : ");
            switch (choix) {
                case 1 -> ajouterProprietaire();
                case 2 -> modifierProprietaire();
                case 3 -> supprimerProprietaire();
                case 4 -> listerProprietaires();
            }
        } while (choix != 0);
    }

    private static void ajouterProprietaire() {
        String nom = lireChaine("Nom : ");
        String prenom = lireChaine("Prénom : ");
        String tel = lireChaine("Téléphone : ");
        String email = lireChaine("Email : ");
        proprietaires.add(new Proprietaire(nom, prenom, tel, email));
        System.out.println("Propriétaire ajouté.");
    }

    private static void modifierProprietaire() {
        if (proprietaires.isEmpty()) { System.out.println("Aucun propriétaire."); return; }
        Proprietaire p = choisirProprietaire();
        if (p == null) return;
        String nom = lireChaine("Nouveau nom : ");
        String prenom = lireChaine("Nouveau prénom : ");
        String tel = lireChaine("Nouveau téléphone : ");
        String email = lireChaine("Nouvel email : ");
        p.modifier(nom, prenom, tel, email);
        System.out.println("Propriétaire modifié.");
    }

    private static void supprimerProprietaire() {
        if (proprietaires.isEmpty()) { System.out.println("Aucun propriétaire."); return; }
        Proprietaire p = choisirProprietaire();
        if (p == null) return;
        // Vérifier qu'il n'a pas de logement loué
        for (Logement l : p.getLogements()) {
            if (l.estLoue()) {
                System.out.println("Impossible : ce propriétaire a un logement loué.");
                return;
            }
        }
        // Supprimer tous ses logements (non loués)
        for (Logement l : new ArrayList<>(p.getLogements()))
            p.retirerLogement(l);
        proprietaires.remove(p);
        System.out.println("Propriétaire supprimé.");
    }

    private static void listerProprietaires() {
        if (proprietaires.isEmpty()) System.out.println("Aucun propriétaire.");
        else for (Proprietaire p : proprietaires) System.out.println(p);
    }

    // ---------- Gestion des logements ----------
    private static void menuGestionLogements() {
        int choix;
        do {
            System.out.println("\n--- Gestion des logements ---");
            System.out.println("1. Ajouter un logement");
            System.out.println("2. Modifier un logement");
            System.out.println("3. Supprimer un logement");
            System.out.println("4. Lister les logements d'un propriétaire");
            System.out.println("0. Retour");
            choix = lireEntier("Choix : ");
            switch (choix) {
                case 1 -> ajouterLogement();
                case 2 -> modifierLogement();
                case 3 -> supprimerLogement();
                case 4 -> listerLogementsParProprietaire();
            }
        } while (choix != 0);
    }

    private static void ajouterLogement() {
        if (proprietaires.isEmpty()) { System.out.println("Aucun propriétaire."); return; }
        if (communes.isEmpty()) { System.out.println("Aucune commune."); return; }
        Proprietaire p = choisirProprietaire();
        if (p == null) return;
        Commune c = choisirCommune();
        if (c == null || c.getQuartiers().isEmpty()) { System.out.println("Cette commune n'a pas de quartier."); return; }
        Quartier q = choisirQuartier(c);
        if (q == null) return;
        String adresse = lireChaine("Adresse : ");
        double superficie = lireDouble("Superficie (m²) : ");
        int pieces = lireEntier("Nombre de pièces : ");
        Logement log = new Logement(adresse, superficie, pieces);
        p.ajouterLogement(log);
        q.ajouterLogement(log);
        System.out.println("Logement ajouté.");
    }

    private static void modifierLogement() {
        if (proprietaires.isEmpty()) { System.out.println("Aucun propriétaire."); return; }
        Proprietaire p = choisirProprietaire();
        if (p == null || p.getLogements().isEmpty()) { System.out.println("Ce propriétaire n'a pas de logement."); return; }
        Logement l = choisirLogement(p);
        if (l == null) return;
        String adresse = lireChaine("Nouvelle adresse : ");
        Double superficie = null;
        try { superficie = lireDouble("Nouvelle superficie : "); } catch(Exception e) {}
        Integer pieces = null;
        try { pieces = lireEntier("Nouveau nb pièces : "); } catch(Exception e) {}
        l.modifier(adresse, superficie, pieces);
        System.out.println("Logement modifié.");
    }

    private static void supprimerLogement() {
        if (proprietaires.isEmpty()) { System.out.println("Aucun propriétaire."); return; }
        Proprietaire p = choisirProprietaire();
        if (p == null || p.getLogements().isEmpty()) { System.out.println("Ce propriétaire n'a pas de logement."); return; }
        Logement l = choisirLogement(p);
        if (l == null) return;
        if (l.estLoue()) { System.out.println("Impossible : logement loué."); return; }
        p.retirerLogement(l);
        System.out.println("Logement supprimé.");
    }

    private static void listerLogementsParProprietaire() {
        if (proprietaires.isEmpty()) { System.out.println("Aucun propriétaire."); return; }
        Proprietaire p = choisirProprietaire();
        if (p == null) return;
        if (p.getLogements().isEmpty()) System.out.println("Aucun logement.");
        else for (Logement l : p.getLogements()) System.out.println(l);
    }

    // ---------- Gestion des locataires ----------
    private static void menuGestionLocataires() {
        int choix;
        do {
            System.out.println("\n--- Gestion des locataires ---");
            System.out.println("1. Ajouter un locataire");
            System.out.println("2. Modifier un locataire");
            System.out.println("3. Supprimer un locataire");
            System.out.println("4. Lister les locataires");
            System.out.println("0. Retour");
            choix = lireEntier("Choix : ");
            switch (choix) {
                case 1 -> ajouterLocataire();
                case 2 -> modifierLocataire();
                case 3 -> supprimerLocataire();
                case 4 -> listerLocataires();
            }
        } while (choix != 0);
    }

    private static void ajouterLocataire() {
        String nom = lireChaine("Nom : ");
        String prenom = lireChaine("Prénom : ");
        String tel = lireChaine("Téléphone : ");
        String email = lireChaine("Email : ");
        String id = lireChaine("Pièce d'identité : ");
        locataires.add(new Locataire(nom, prenom, tel, email, id));
        System.out.println("Locataire ajouté.");
    }

    private static void modifierLocataire() {
        if (locataires.isEmpty()) { System.out.println("Aucun locataire."); return; }
        Locataire l = choisirLocataire();
        if (l == null) return;
        String nom = lireChaine("Nouveau nom : ");
        String prenom = lireChaine("Nouveau prénom : ");
        String tel = lireChaine("Nouveau téléphone : ");
        String email = lireChaine("Nouvel email : ");
        String id = lireChaine("Nouvelle pièce d'identité : ");
        l.modifier(nom, prenom, tel, email, id);
        System.out.println("Locataire modifié.");
    }

    private static void supprimerLocataire() {
        if (locataires.isEmpty()) { System.out.println("Aucun locataire."); return; }
        Locataire l = choisirLocataire();
        if (l == null) return;
        // Vérifier qu'il n'a pas de contrat actif
        if (l.getLogementActuel() != null) {
            System.out.println("Impossible : ce locataire a un contrat actif.");
            return;
        }
        locataires.remove(l);
        System.out.println("Locataire supprimé.");
    }

    private static void listerLocataires() {
        if (locataires.isEmpty()) System.out.println("Aucun locataire.");
        else for (Locataire l : locataires) System.out.println(l);
    }

    // ---------- Gestion des contrats ----------
    private static void menuGestionContrats() {
        int choix;
        do {
            System.out.println("\n--- Gestion des contrats ---");
            System.out.println("1. Créer un contrat");
            System.out.println("2. Résilier un contrat");
            System.out.println("3. Lister les contrats");
            System.out.println("0. Retour");
            choix = lireEntier("Choix : ");
            switch (choix) {
                case 1 -> creerContrat();
                case 2 -> resillerContrat();
                case 3 -> listerContrats();
            }
        } while (choix != 0);
    }

    private static void creerContrat() {
        if (locataires.isEmpty()) { System.out.println("Aucun locataire."); return; }
        List<Logement> logementsDispo = new ArrayList<>();
        for (Proprietaire p : proprietaires)
            for (Logement l : p.getLogements())
                if (!l.estLoue())
                    logementsDispo.add(l);
        if (logementsDispo.isEmpty()) { System.out.println("Aucun logement disponible."); return; }

        Locataire loc = choisirLocataire();
        if (loc == null) return;
        System.out.println("Logements disponibles :");
        for (int i = 0; i < logementsDispo.size(); i++)
            System.out.println((i+1) + ". " + logementsDispo.get(i));
        int idx = lireEntier("Choix : ") - 1;
        if (idx < 0 || idx >= logementsDispo.size()) return;
        Logement logement = logementsDispo.get(idx);
        Proprietaire prop = logement.getProprietaire();

        Date debut = lireDate("Date début (yyyy-mm-dd) : ");
        Date fin = lireDate("Date fin (yyyy-mm-dd) : ");
        double loyer = lireDouble("Loyer mensuel : ");
        new Contrat(debut, fin, loyer, logement, prop, loc);
        System.out.println("Contrat créé.");
    }

    private static void resillerContrat() {
        List<Contrat> contrats = new ArrayList<>();
        for (Proprietaire p : proprietaires)
            contrats.addAll(p.getContrats());
        if (contrats.isEmpty()) { System.out.println("Aucun contrat."); return; }
        System.out.println("Liste des contrats :");
        for (int i = 0; i < contrats.size(); i++)
            System.out.println((i+1) + ". " + contrats.get(i));
        int idx = lireEntier("Lequel résilier ? ") - 1;
        if (idx < 0 || idx >= contrats.size()) return;
        Contrat c = contrats.get(idx);
        c.resilier();
        System.out.println("Contrat résilié.");
    }

    private static void listerContrats() {
        boolean aucun = true;
        for (Proprietaire p : proprietaires) {
            for (Contrat c : p.getContrats()) {
                System.out.println(c);
                aucun = false;
            }
        }
        if (aucun) System.out.println("Aucun contrat.");
    }

    // ---------- Consultation habitants ----------
    private static void consulterHabitants() {
        if (communes.isEmpty()) { System.out.println("Aucune commune."); return; }
        Commune c = choisirCommune();
        if (c == null) return;
        System.out.println("1. Tous les habitants de la commune");
        System.out.println("2. Habitants par quartier");
        int choix = lireEntier("Choix : ");
        if (choix == 1) c.afficherHabitants();
        else if (choix == 2) c.afficherHabitantsParQuartier();
        else System.out.println("Choix invalide.");
    }

    // ---------- Utilitaires de saisie ----------
    private static Commune choisirCommune() {
        System.out.println("Choisissez une commune :");
        for (int i = 0; i < communes.size(); i++)
            System.out.println((i+1) + ". " + communes.get(i).getNom());
        int idx = lireEntier("Choix : ") - 1;
        return (idx >= 0 && idx < communes.size()) ? communes.get(idx) : null;
    }

    private static Quartier choisirQuartier(Commune c) {
        List<Quartier> qs = c.getQuartiers();
        for (int i = 0; i < qs.size(); i++)
            System.out.println((i+1) + ". " + qs.get(i).getNom());
        int idx = lireEntier("Choix : ") - 1;
        return (idx >= 0 && idx < qs.size()) ? qs.get(idx) : null;
    }

    private static Proprietaire choisirProprietaire() {
        for (int i = 0; i < proprietaires.size(); i++)
            System.out.println((i+1) + ". " + proprietaires.get(i).getNomComplet());
        int idx = lireEntier("Choix : ") - 1;
        return (idx >= 0 && idx < proprietaires.size()) ? proprietaires.get(idx) : null;
    }

    private static Logement choisirLogement(Proprietaire p) {
        List<Logement> ls = p.getLogements();
        for (int i = 0; i < ls.size(); i++)
            System.out.println((i+1) + ". " + ls.get(i));
        int idx = lireEntier("Choix : ") - 1;
        return (idx >= 0 && idx < ls.size()) ? ls.get(idx) : null;
    }

    private static Locataire choisirLocataire() {
        for (int i = 0; i < locataires.size(); i++)
            System.out.println((i+1) + ". " + locataires.get(i).getNomComplet());
        int idx = lireEntier("Choix : ") - 1;
        return (idx >= 0 && idx < locataires.size()) ? locataires.get(idx) : null;
    }

    private static String lireChaine(String msg) {
        System.out.print(msg);
        return scanner.nextLine();
    }

    private static int lireEntier(String msg) {
        System.out.print(msg);
        while (!scanner.hasNextInt()) {
            System.out.print("Nombre attendu : ");
            scanner.next();
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }

    private static double lireDouble(String msg) {
        System.out.print(msg);
        while (!scanner.hasNextDouble()) {
            System.out.print("Nombre décimal attendu : ");
            scanner.next();
        }
        double val = scanner.nextDouble();
        scanner.nextLine();
        return val;
    }

    private static Date lireDate(String msg) {
        System.out.print(msg);
        String dateStr = scanner.nextLine();
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (Exception e) {
            System.out.println("Format invalide, utilisation date actuelle.");
            return new Date();
        }
    }
}