import { Moment } from 'moment';
import { IFidele } from 'app/shared/model//fidele.model';
import { IEmploye } from 'app/shared/model//employe.model';

export interface IVersement {
    id?: number;
    dateVersement?: Moment;
    montantVersement?: number;
    fidele?: IFidele;
    employe?: IEmploye;
}

export class Versement implements IVersement {
    constructor(
        public id?: number,
        public dateVersement?: Moment,
        public montantVersement?: number,
        public fidele?: IFidele,
        public employe?: IEmploye
    ) {}
}
