# Dossier Technique – Projet Android GSB Frais

**Session BTS SIO 2025 – Épreuve E6 : Conception et développement d’applications (option SLAM)**  
**Développeur : Menoni Fabian**  
**Modalité : Individuelle**  
**Période de réalisation : Septembre 2024 – Avril 2025**  
**Environnement : Java, Android Studio, Retrofit, MySQL, Material Design**  

---

## Table des Matières
1. [Présentation Générale du Projet](#1-présentation-générale-du-projet)
2. [Description de l’Architecture Technique](#2-description-de-larchitecture-technique)
   - 2.1. [Structure du Projet](#21-structure-du-projet)
3. [Détail des Fonctionnalités](#3-détail-des-fonctionnalités)
   - 3.1. [Saisie et Gestion des Frais](#31-saisie-et-gestion-des-frais)
   - 3.2. [Interface Administrateur et Statistiques](#32-interface-administrateur-et-statistiques)
4. [Analyse Approfondie des Activités](#4-analyse-approfondie-des-activités)
5. [Organisation du Code et des Composants](#5-organisation-du-code-et-des-composants)
6. [Maintenance et Évolutivité](#6-maintenance-et-évolutivité)
7. [Commentaires et Documentation Interne](#7-commentaires-et-documentation-interne)
8. [Conclusion et Perspectives d’Évolution](#8-conclusion-et-perspectives-dévolution)

---

## 1. Présentation Générale du Projet

Le projet **GSB Frais Android** a pour objectif de créer une application mobile Android permettant aux utilisateurs de saisir, consulter et gérer leurs frais professionnels.

Les principaux objectifs sont :

- **Conception et développement** : Créer une interface mobile intuitive permettant de saisir des frais, de consulter l'historique des frais et de gérer les frais.
- **Maintenance** : Utiliser une architecture modulaire pour faciliter l’évolution de l’application et la correction des erreurs.
- **Sécurité des données** : Garantir la protection des données personnelles avec l’utilisation de Firebase pour le backend, et un stockage local sécurisé via SQLite.

L'application Android est développée avec **Android Studio** en utilisant **Kotlin** (ou Java), **Firebase** pour la gestion des données à distance et **SQLite** pour le stockage local. **Material Design** est utilisé pour assurer une interface moderne et responsive.

---

## 2. Description de l’Architecture Technique

### 2.1. Structure du Projet

La structure du projet suit les bonnes pratiques d'architecture Android, en adoptant une approche **MVVM (Model-View-ViewModel)** avec des composants comme **LiveData** et **ViewModel** pour gérer la logique de l'UI de manière asynchrone.

- **/src**  
  Contient le code source de l’application, organisé par packages.
  - **activity/** : Contient les activités qui gèrent les interactions avec l'utilisateur.
  - **model/** : Contient les classes modèles, telles que `Frais`, `Utilisateur`, etc.
  - **viewmodel/** : Contient les classes `ViewModel` qui gèrent la logique métier de l’application.
  - **repository/** : Contient les classes pour la gestion des données locales (SQLite) et distantes (Firebase).

- **/res**  
  Contient les ressources de l’application.
  - **layout/** : Contient les fichiers XML pour les interfaces utilisateur.
  - **values/** : Contient les chaînes de caractères et autres valeurs définies dans l’application.
  - **drawable/** : Contient les ressources graphiques et icônes.

- **/config**  
  Contient les fichiers de configuration Firebase et autres services.

- **/assets**  
  Contient les fichiers statiques comme les polices ou images personnalisées.

---

## 3. Détail des Fonctionnalités

### 3.1. Saisie et Gestion des Frais

- **Saisie des Frais** :  
  Formulaire avec validation des données et possibilité d’ajouter des frais pour des activités spécifiques. Utilisation de **RecyclerView** pour afficher les frais en liste. Validation des données effectuée côté client et côté serveur.

- **Consultation et Édition** :  
  Liste des frais saisie par l’utilisateur. Possibilité d’édition ou de suppression des frais à tout moment via un menu contextuel.

- **Traitement et Calcul** :  
  Calcul automatique du total des frais avec gestion des différentes catégories (transport, repas, hébergement, etc.).

### 3.2. Interface Administrateur et Statistiques

- **Mécanismes de Validation** :  
  Les administrateurs peuvent valider ou rejeter des frais via une interface dédiée.
  
- **Statistiques** :  
  Les administrateurs peuvent consulter des statistiques sur les frais, tels que le total des dépenses par utilisateur, par catégorie, etc.

---

## 4. Analyse Approfondie des Activités

Les activités dans Android sont responsables de l’interface utilisateur et de la gestion des interactions utilisateur.

- **Réception des Intentions** :  
  Chaque activité réagit à des actions (clics de boutons, changements de formulaire) en utilisant des **Intent** pour passer d’une activité à une autre.

- **Gestion des Données** :  
  Les `ViewModel` gèrent la logique métier en récupérant les données du **Repository**, qui interagit avec Firebase et SQLite.

- **Rendu des Réponses** :  
  Les activités récupèrent les données via **LiveData** et mettent à jour l’UI de manière réactive lorsque les données sont disponibles.

---

## 5. Organisation du Code et des Composants

### 5.1. Dossier **/src**

- **Activités (/src/activity/)** :  
  Chaque activité est responsable d’une vue spécifique. Les interactions de l’utilisateur (clics, saisie) sont traitées dans ces activités.
  
- **Modèles (/src/model/)** :  
  Les classes modèles représentent les objets métiers, comme `Frais`, `Utilisateur`, etc. Ces classes sont utilisées pour gérer les données en interaction avec la base de données locale ou distante.

- **ViewModels (/src/viewmodel/)** :  
  Les `ViewModel` sont responsables de la logique métier, gérant l’accès aux données via les **LiveData** pour une interface réactive.

- **Repositories (/src/repository/)** :  
  Les repositories sont chargés de l’accès aux données locales et distantes. Par exemple, un `FraisRepository` qui récupère les frais depuis Firebase ou SQLite.

### 5.2. Dossier **/res**

- **Layouts (/res/layout/)** :  
  Contient les fichiers XML pour définir les interfaces des activités.  
  *Exemple :* `activity_main.xml` pour l’écran principal et `activity_add_frais.xml` pour le formulaire de saisie.

- **Values (/res/values/)** :  
  Contient des fichiers comme `strings.xml`, `colors.xml`, et `styles.xml` pour gérer les chaînes de caractères, couleurs et styles de l’application.

- **Drawable (/res/drawable/)** :  
  Contient les icônes et images utilisées dans l’application.

---

## 6. Maintenance et Évolutivité

### 6.1. Maintenance Corrective

- **Tests :**  
  Des tests unitaires et instrumentés sont effectués pour garantir que chaque fonctionnalité fonctionne correctement. **JUnit** et **Espresso** sont utilisés pour tester la logique et l’interface.

- **Débogage :**  
  Utilisation de **Android Studio Debugger** pour identifier et résoudre les problèmes. Les **logs Logcat** aident à diagnostiquer les erreurs.

- **Suivi des Bugs :**  
  Utilisation de **GitHub Issues** pour la gestion des bugs et des demandes de fonctionnalités.

### 6.2. Maintenance Évolutive

- **Extensibilité :**  
  L’application est conçue pour être facilement évolutive, avec une architecture modulaire (MVVM), permettant l’ajout facile de nouvelles fonctionnalités (ex. exportation de données, ajout de nouveaux frais).

- **Mises à jour de sécurité :**  
  Surveillance régulière des mises à jour de sécurité pour Firebase, SQLite, et les bibliothèques tierces utilisées dans l’application.

---

## 7. Commentaires et Documentation Interne

- **Commentaires dans le Code :**  
  Chaque méthode critique est commentée, expliquant son rôle, ses paramètres et son comportement.

- **Documentation Automatique :**  
  Des outils comme **KDoc** sont utilisés pour générer la documentation à partir des commentaires dans le code.

- **Fichiers README :**  
  Un fichier README est présent dans chaque dossier important pour expliquer sa structure et son rôle dans le projet.

---

## 8. Conclusion et Perspectives d’Évolution

Le dossier technique offre une vue d’ensemble complète du projet **GSB Frais Android**. Il décrit l’architecture, les composants du code, et la gestion des fonctionnalités de l’application. Cette documentation permet à un autre développeur de maintenir et d’évoluer l’application.

**Perspectives d’évolution :**
- **Ajout d’un module API** pour intégrer l’application avec un backend externe.
- **Amélioration de la sécurité** avec l’ajout de fonctionnalités de chiffrement pour les données locales.
- **Ajout de notifications push** via Firebase Cloud Messaging (FCM).

---

*Fin du Dossier Technique*
