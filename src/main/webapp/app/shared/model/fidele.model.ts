import { ICarte } from 'app/shared/model//carte.model';
import { ICEB } from 'app/shared/model//ceb.model';

export const enum Categorie {
    ANONYME = 'ANONYME',
    FAMILLE = 'FAMILLE',
    CHRETIEN = 'CHRETIEN'
}

export interface IFidele {
    id?: number;
    nom?: string;
    prenom?: string;
    categorie?: Categorie;
    montantVerse?: number;
    montantRestant?: number;
    carte?: ICarte;
    ceb?: ICEB;
}

export class Fidele implements IFidele {
    constructor(
        public id?: number,
        public nom?: string,
        public prenom?: string,
        public categorie?: Categorie,
        public montantVerse?: number,
        public montantRestant?: number,
        public carte?: ICarte,
        public ceb?: ICEB
    ) {}
}
