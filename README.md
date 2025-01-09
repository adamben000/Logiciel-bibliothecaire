# Logiciel Bibliothécaire

Un système de gestion de bibliothèque développé par Adam Benmoussa, Achraf Benchoubane et Laurick Mukula.

## Description du Projet

Notre logiciel de gestion de bibliothèque offre une solution complète aux défis actuels d'organisation et de gestion des livres. Le système propose une interface utilisateur intuitive permettant aux utilisateurs d'emprunter et de retourner des livres facilement, tout en offrant aux administrateurs des outils puissants de gestion.

Diagramme de classes:
Image: 
![image](https://github.com/user-attachments/assets/26e048df-41a7-4019-ac26-47f8e10a3406)

Mira:
https://miro.com/welcomeonboard/SkJaYWtqaERzMFV6Wm1WdUdCUkhad2EwN1QxME43eVJOSUoyZjM2R2RweXBsVW55cWZRc1ZONEgwY25ScGlxWmFXbGNiN3VmMFhkamhpaEZjSEVwTzROZjZSSjBzMDNHRTB6YTNOWlRkN0tXckZkM1VhVWF2SlpwVDNhTGRXTG0hZQ==?share_link_id=850374970762

### Problématiques Résolues

- Désorganisation des stocks de livres
- Difficultés dans la gestion des retours
- Interfaces utilisateur peu adaptées
- Besoin de modernisation face à la concurrence

## Fonctionnalités Principales

### Pour les Utilisateurs
- Système de connexion et création de compte
- Interface pour la recherche de livres
- Emprunt et retour de livres simplifiés
- Gestion du profil utilisateur
- Consultation des emprunts en cours

### Pour les Administrateurs
- Gestion complète des utilisateurs
- Gestion du catalogue de livres
- Suivi des emprunts et retours
- Interface d'administration

## Architecture Technique

### Base de Données
Le système utilise une structure de fichiers texte pour stocker les données :
- `emprunts.txt` : Gestion des emprunts
- `livres.txt` : Catalogue des livres
- `utilisateurs.txt` : Informations des utilisateurs

### Classes Principales
- `Database` : Gestion des données
- `Livre` : Modèle de données pour les livres
- `Utilisateur` : Modèle de données pour les utilisateurs

Image Page connexion:
![image](https://github.com/user-attachments/assets/db442e5c-37eb-43cd-9fd7-48d2a0da2af6)

Image Page Registre:
![image](https://github.com/user-attachments/assets/4ca25141-0bad-43bc-b586-4d639c841a9a)

Image Page d'utilisateur:
![image](https://github.com/user-attachments/assets/c09d25e0-0ad8-429d-ae48-96a87e4b493a)

Image Page Administrateur options:
![image](https://github.com/user-attachments/assets/81e65ce4-e3ee-471e-a56b-f7bd83d3094d)

Image Page Utilisateurs:
![image](https://github.com/user-attachments/assets/d54d9c59-76f7-46b4-9f6d-a5aaa6a347a4)

Image Page Livres:
![image](https://github.com/user-attachments/assets/392cb558-8280-4b04-a3c6-2ee94e2f7d18)

Image Page Emprunts:
![image](https://github.com/user-attachments/assets/37f43682-96e4-4b57-a0e3-d9b0af411f30)

Mira:
https://miro.com/welcomeonboard/SkJaYWtqaERzMFV6Wm1WdUdCUkhad2EwN1QxME43eVJOSUoyZjM2R2RweXBsVW55cWZRc1ZONEgwY25ScGlxWmFXbGNiN3VmMFhkamhpaEZjSEVwTzROZjZSSjBzMDNHRTB6YTNOWlRkN0tXckZkM1VhVWF2SlpwVDNhTGRXTG0hZQ==?share_link_id=850374970762

## Interface Utilisateur

Le logiciel propose plusieurs interfaces adaptées aux différents utilisateurs :
- Page de connexion/inscription
- Interface utilisateur pour la gestion des emprunts
- Interface administrateur pour la gestion du système
- Paramètres utilisateur pour la personnalisation du compte

## Tests et Validation

Entrées utilisateur/mot de passe :

Champs non vides.
Respect des critères (longueur, caractères spéciaux, etc.).
Rejet des doublons et des espaces inutiles.

Quantité des livres (Admin Livres) :

Vérifier les nombres valides (positifs).
Rejet des champs vides ou non numériques.

Création d’un livre :

Rejet des champs vides ou contenant uniquement des espaces.
Validation des données obligatoires (titre, auteur, etc.).

## Équipe de Développement

- **Adam Benmoussa** : Gestions des erreurs, Base de données, Documentation
- **Achraf Benchoubane** : Interface utilisateur, design GUI, backend GUI,Documentation
- **Laurick Mukula** : , Tests

## Technologies Utilisées

- Java
- Swing (Interface graphique)
- Système de fichiers texte pour le stockage des données

## Perspectives d'Évolution

- Amélioration de l'interface utilisateur
- Ajout de nouvelles fonctionnalités de gestion
- Optimisation des performances
- Extension des capacités de recherche

Image Diagramme Grantt:
![image](https://github.com/user-attachments/assets/63df534d-a31b-4118-b143-02cfa603d988)

Mira:
https://miro.com/welcomeonboard/SkJaYWtqaERzMFV6Wm1WdUdCUkhad2EwN1QxME43eVJOSUoyZjM2R2RweXBsVW55cWZRc1ZONEgwY25ScGlxWmFXbGNiN3VmMFhkamhpaEZjSEVwTzROZjZSSjBzMDNHRTB6YTNOWlRkN0tXckZkM1VhVWF2SlpwVDNhTGRXTG0hZQ==?share_link_id=850374970762
---
Projet développé dans le cadre du cours ICS4U.
