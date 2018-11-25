import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CEB } from 'app/shared/model/ceb.model';
import { CEBService } from './ceb.service';
import { CEBComponent } from './ceb.component';
import { CEBDetailComponent } from './ceb-detail.component';
import { CEBUpdateComponent } from './ceb-update.component';
import { CEBDeletePopupComponent } from './ceb-delete-dialog.component';
import { ICEB } from 'app/shared/model/ceb.model';

@Injectable({ providedIn: 'root' })
export class CEBResolve implements Resolve<ICEB> {
    constructor(private service: CEBService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CEB> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CEB>) => response.ok),
                map((cEB: HttpResponse<CEB>) => cEB.body)
            );
        }
        return of(new CEB());
    }
}

export const cEBRoute: Routes = [
    {
        path: 'ceb',
        component: CEBComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'stBarnabeApp.cEB.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ceb/:id/view',
        component: CEBDetailComponent,
        resolve: {
            cEB: CEBResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'stBarnabeApp.cEB.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ceb/new',
        component: CEBUpdateComponent,
        resolve: {
            cEB: CEBResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'stBarnabeApp.cEB.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ceb/:id/edit',
        component: CEBUpdateComponent,
        resolve: {
            cEB: CEBResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'stBarnabeApp.cEB.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cEBPopupRoute: Routes = [
    {
        path: 'ceb/:id/delete',
        component: CEBDeletePopupComponent,
        resolve: {
            cEB: CEBResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'stBarnabeApp.cEB.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
