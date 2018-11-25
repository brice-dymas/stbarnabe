import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFidele } from 'app/shared/model/fidele.model';
import { IFideleDto } from 'app/shared/model/fideledto.model';

type EntityResponseType = HttpResponse<IFidele>;
type EntityResponseTypeDto = HttpResponse<IFideleDto>;
type EntityArrayResponseType = HttpResponse<IFidele[]>;

@Injectable({ providedIn: 'root' })
export class FideleService {
    public resourceUrl = SERVER_API_URL + 'api/fideles';
    public fideleDtoUrl = SERVER_API_URL + 'api/fideles-versements';

    constructor(private http: HttpClient) {}

    create(fidele: IFidele): Observable<EntityResponseType> {
        return this.http.post<IFidele>(this.resourceUrl, fidele, { observe: 'response' });
    }

    update(fidele: IFidele): Observable<EntityResponseType> {
        return this.http.put<IFidele>(this.resourceUrl, fidele, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IFidele>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    findComplete(id: number): Observable<EntityResponseTypeDto> {
        console.log('Dans la methode de la recherche des versements du fidele');
        return this.http.get<IFideleDto>(`${this.fideleDtoUrl}/${id}`, { observe: 'response' });
    }

    deleteComplete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.fideleDtoUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFidele[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
