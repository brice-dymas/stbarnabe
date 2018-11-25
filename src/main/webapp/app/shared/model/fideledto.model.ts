import { IFidele } from 'app/shared/model/fidele.model';
import { IVersement } from 'app/shared/model/versement.model';

export interface IFideleDto {
    fidele?: IFidele;
    versements?: IVersement[];
}

export class FideleDto implements IFideleDto {
    constructor(public fidele?: IFidele, public versements?: IVersement[]) {}
}
