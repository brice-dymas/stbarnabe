export interface ICarte {
    id?: number;
    couleur?: string;
    montantMin?: number;
}

export class Carte implements ICarte {
    constructor(public id?: number, public couleur?: string, public montantMin?: number) {}
}
