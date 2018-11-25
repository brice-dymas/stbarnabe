export interface ICEB {
    id?: number;
    nom?: string;
}

export class CEB implements ICEB {
    constructor(public id?: number, public nom?: string) {}
}
