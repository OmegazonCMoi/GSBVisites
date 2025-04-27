# Dossier Technique – Projet Android GSB Frais : Gestion des Visites, Praticiens, et Motifs

**Session BTS SIO 2025 – Épreuve E6 : Conception et développement d’applications (option SLAM)**  
**Développeur : Menoni Fabian**  
**Modalité : Individuelle**  
**Période de réalisation : Septembre 2024 – Avril 2025**  
**Environnement : Java, Android Studio, API Express, MySQL, Retrofit, Material Design**  

---

## Table des Matières
1. [Présentation Générale du Projet](#1-présentation-générale-du-projet)
2. [Description de l’Architecture Technique](#2-description-de-larchitecture-technique)
   - 2.1. [Structure du Projet](#21-structure-du-projet)
3. [Gestion des Visites, Praticiens, et Motifs](#3-gestion-des-visites-praticiens-et-motifs)
   - 3.1. [Gestion des Visites](#31-gestion-des-visites)
   - 3.2. [Gestion des Praticiens](#32-gestion-des-praticiens)
   - 3.3. [Gestion des Motifs](#33-gestion-des-motifs)
4. [Analyse Approfondie des Composants](#4-analyse-approfondie-des-composants)
5. [Organisation du Code et des Templates](#5-organisation-du-code-et-des-templates)
6. [Maintenance et Évolutivité](#6-maintenance-et-évolutivité)
7. [Commentaires et Documentation Interne](#7-commentaires-et-documentation-interne)
8. [Conclusion et Perspectives d’Évolution](#8-conclusion-et-perspectives-dévolution)

---

## 1. Présentation Générale du Projet

Le projet **GSB Frais** est une application Android permettant de gérer les visites professionnelles effectuées par des visiteurs chez des praticiens. L'application permet aux utilisateurs de saisir des visites, consulter les praticiens disponibles, et gérer les motifs de leurs visites. Elle communique avec une **API Express** qui se charge de l’accès aux données stockées dans une base **MySQL**.

Les principaux objectifs de l'application sont les suivants :
- **Gestion des visites** : Permettre aux visiteurs de saisir des visites, de consulter et modifier les informations liées aux visites passées.
- **Consultation des praticiens et motifs** : Permettre aux utilisateurs de consulter la liste des praticiens disponibles et des motifs de visites proposés.
- **Interaction avec une API Express** : L'application interagit avec une API Express qui gère les requêtes vers la base de données MySQL.

---

## 2. Description de l’Architecture Technique

### 2.1. Structure du Projet

L'architecture du projet Android repose sur **Java/Kotlin** avec **Android Studio** pour le développement mobile. Le backend de l'application est une API RESTful développée avec **Express.js**, qui interagit avec une base de données **MySQL**. La communication entre l'application Android et l'API Express se fait via la bibliothèque **Retrofit**.

#### Structure de l’application Android :

- **/gsbofficiel**  
  Contient le code métier et les composants Android.
  - **/api** : Contient les classes qui gèrent la communication avec l'API Express (utilisation de Retrofit pour envoyer des requêtes HTTP).

- **/res**  
  Contient les ressources de l'application (layout XML, strings, images, etc.).

#### Structure de l’API Express :

- **/models** : Contient les modèles Mongoose pour chaque entité (Visite, Visiteur, Praticien, Motif).
- **/routes** : Contient les routes qui définissent les différentes API pour la gestion des visites, praticiens, et motifs.
- **/controllers** : Contient la logique métier pour chaque route.

---

## 3. Gestion des Visites, Praticiens, et Motifs

### 3.1. Gestion des Visites

- **Modèle Visite** :  
  Le modèle Visite représente une visite effectuée par un visiteur chez un praticien. Il contient des informations telles que la date de la visite, les commentaires associés, le praticien concerné, et le motif de la visite.
  
  La classe `Visite` contient les informations suivantes :
  - **id** : Identifiant unique de la visite.
  - **dateVisite** : Date et heure de la visite.
  - **commentaire** : Commentaires liés à la visite.
  - **visiteur** : Visiteur ayant effectué la visite.
  - **praticien** : Praticien concerné.
  - **motif** : Motif de la visite.

  Le modèle **Visite** en **Java/Kotlin** utilise Retrofit pour envoyer et recevoir des données via l'API Express.

- **Interaction avec l'API Express** :  
  Les données des visites sont envoyées à l'API Express via des requêtes HTTP POST pour la création, GET pour la consultation, PUT pour la mise à jour et DELETE pour la suppression.

  Exemple de requête POST pour créer une visite :
  ```java
  @POST("visites")
  Call<Void> createVisite(@Header("Authorization") String token, @Body Map<String, Object> visite);
  ```

### 3.2. Gestion des Praticiens

- **Modèle Praticien** :  
  Le modèle Praticien contient des informations sur chaque praticien, comme son nom, sa spécialité, et son identifiant unique.  
  Les données des praticiens sont récupérées via une requête GET à l'API Express.

  Exemple de requête GET pour récupérer les praticiens :
  ```java
  @GET("praticiens")
  Call<List<Praticien>> getPraticiens(@Header("Authorization") String token);
  ```

### 3.3. Gestion des Motifs

- **Modèle Motif** :  
  Le modèle Motif contient des informations sur les motifs de visites. Un motif peut être associé à une ou plusieurs visites.  
  Les motifs sont récupérés via une requête GET à l'API Express.

  Exemple de requête GET pour récupérer les motifs :
  ```java
  @GET("motifs")
  Call<List<Motif>> getMotifs(@Header("Authorization") String token);
  ```

---

## 4. Analyse Approfondie des Composants

Les principaux composants de l’application Android sont :

- **Les Activités et Fragments** :  
  Chaque écran de l’application (par exemple, écran de création d’une visite, liste des praticiens, etc.) est géré par une activité ou un fragment. Ces composants interagissent avec l'API pour afficher et envoyer les données.

- **Les Modèles de Données** :  
  Chaque entité (Visite, Praticien, Motif, etc.) est représentée par un modèle de données en Java/Kotlin, facilitant la sérialisation et la désérialisation des objets entre l'application et l'API.

- **Retrofit** :  
  La bibliothèque Retrofit est utilisée pour gérer les requêtes HTTP entre l’application et l’API Express. Elle simplifie le traitement des réponses JSON et la gestion des erreurs.

---

## 5. Organisation du Code et des Templates

Le code est organisé de manière modulaire, chaque partie ayant sa propre responsabilité :
- **Activités et Fragments** pour la présentation des données à l'utilisateur.
- **Services API** pour la gestion des appels à l’API Express.
- **Modèles de données** pour la gestion des objets Visite, Praticien, Motif, etc.
- **Utilitaires** pour la gestion des erreurs et des données (formatage des dates, gestion des préférences utilisateurs, etc.).

---

## 6. Maintenance et Évolutivité

### 6.1. Maintenance Corrective

- **Tests unitaires** :  
  Mise en place de tests unitaires pour valider la logique métier et la communication avec l'API.

- **Suivi des erreurs** :  
  Utilisation de la bibliothèque **Crashlytics** ou des outils de log pour suivre les erreurs en production.

### 6.2. Maintenance Évolutive

- **Ajout de nouvelles fonctionnalités** :  
  Le code est conçu pour être facilement extensible, permettant l’ajout de nouvelles fonctionnalités (par exemple, gestion des utilisateurs, notifications, etc.).

---

## 7. Commentaires et Documentation Interne

Chaque méthode et classe est documentée avec des commentaires clairs et précis pour faciliter la maintenance et l’évolution du code.

---

## 8. Conclusion et Perspectives d’Évolution

Ce dossier technique décrit l’architecture et les principaux composants du projet Android **GSB Frais**, qui permet de gérer les visites professionnelles, les praticiens, et les motifs via une application mobile connectée à une API Express et une base de données MySQL.  
Les perspectives d’évolution incluent l’ajout de nouvelles fonctionnalités telles que la gestion des utilisateurs et l’intégration avec d’autres services.

---

*Fin du Dossier Technique*
