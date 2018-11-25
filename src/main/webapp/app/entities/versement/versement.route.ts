import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Versement } from 'app/shared/model/versement.model';
import { VersementService } from './versement.service';
import { VersementComponent } from './versement.component';
import { VersementDetailComponent } from './versement-detail.component';
import { VersementUpdateComponent } from './versement-update.component';
import { VersementDeletePopupComponent } from './versement-delete-dialog.component';
import { IVersement } from 'app/shared/model/versement.model';

@Injectable({ providedIn: 'root' })
export class VersementResolve implements Resolve<IVersement> {
    constructor(private service: VersementService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Versement> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Versement>) => response.ok),
                map((versement: HttpResponse<Versement>) => versement.body)
            );
        }
        return of(new Versement());
    }
}

export const versementRoute: Routes = [
    {
        path: 'versement',
        component: VersementComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'stBarnabeApp.versement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'versement/:id/view',
        component: VersementDetailComponent,
        resolve: {
            versement: VersementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'stBarnabeApp.versement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'versement/new',
        component: VersementUpdateComponent,
        resolve: {
            versement: VersementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'stBarnabeApp.versement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'versement/:id/edit',
        component: VersementUpdateComponent,
        resolve: {
            versement: VersementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'stBarnabeApp.versement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const versementPopupRoute: Routes = [
    {
        path: 'versement/:id/delete',
        component: VersementDeletePopupComponent,
        resolve: {
            versement: VersementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'stBarnabeApp.versement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
