import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICarte } from 'app/shared/model/carte.model';

type EntityResponseType = HttpResponse<ICarte>;
type EntityArrayResponseType = HttpResponse<ICarte[]>;

@Injectable({ providedIn: 'root' })
export class CarteService {
    public resourceUrl = SERVER_API_URL + 'api/cartes';

    constructor(private http: HttpClient) {}

    create(carte: ICarte): Observable<EntityResponseType> {
        return this.http.post<ICarte>(this.resourceUrl, carte, { observe: 'response' });
    }

    update(carte: ICarte): Observable<EntityResponseType> {
        return this.http.put<ICarte>(this.resourceUrl, carte, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICarte>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICarte[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
