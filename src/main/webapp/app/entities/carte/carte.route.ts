import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Carte } from 'app/shared/model/carte.model';
import { CarteService } from './carte.service';
import { CarteComponent } from './carte.component';
import { CarteDetailComponent } from './carte-detail.component';
import { CarteUpdateComponent } from './carte-update.component';
import { CarteDeletePopupComponent } from './carte-delete-dialog.component';
import { ICarte } from 'app/shared/model/carte.model';

@Injectable({ providedIn: 'root' })
export class CarteResolve implements Resolve<ICarte> {
    constructor(private service: CarteService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Carte> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Carte>) => response.ok),
                map((carte: HttpResponse<Carte>) => carte.body)
            );
        }
        return of(new Carte());
    }
}

export const carteRoute: Routes = [
    {
        path: 'carte',
        component: CarteComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'stBarnabeApp.carte.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'carte/:id/view',
        component: CarteDetailComponent,
        resolve: {
            carte: CarteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'stBarnabeApp.carte.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'carte/new',
        component: CarteUpdateComponent,
        resolve: {
            carte: CarteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'stBarnabeApp.carte.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'carte/:id/edit',
        component: CarteUpdateComponent,
        resolve: {
            carte: CarteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'stBarnabeApp.carte.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cartePopupRoute: Routes = [
    {
        path: 'carte/:id/delete',
        component: CarteDeletePopupComponent,
        resolve: {
            carte: CarteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'stBarnabeApp.carte.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
