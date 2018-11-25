import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICEB } from 'app/shared/model/ceb.model';

type EntityResponseType = HttpResponse<ICEB>;
type EntityArrayResponseType = HttpResponse<ICEB[]>;

@Injectable({ providedIn: 'root' })
export class CEBService {
    public resourceUrl = SERVER_API_URL + 'api/cebs';

    constructor(private http: HttpClient) {}

    create(cEB: ICEB): Observable<EntityResponseType> {
        return this.http.post<ICEB>(this.resourceUrl, cEB, { observe: 'response' });
    }

    update(cEB: ICEB): Observable<EntityResponseType> {
        return this.http.put<ICEB>(this.resourceUrl, cEB, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICEB>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICEB[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
