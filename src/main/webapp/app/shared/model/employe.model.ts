import { IUser } from 'app/core/user/user.model';

export interface IEmploye {
    id?: number;
    telephone?: string;
    user?: IUser;
}

export class Employe implements IEmploye {
    constructor(public id?: number, public telephone?: string, public user?: IUser) {}
}
