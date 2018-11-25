import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Fidele } from 'app/shared/model/fidele.model';
import { FideleDto, IFideleDto } from 'app/shared/model/fideledto.model';
import { FideleService } from './fidele.service';
import { FideleComponent } from './fidele.component';
import { FideleDetailComponent } from './fidele-detail.component';
import { FideleUpdateComponent } from './fidele-update.component';
import { FideleDeletePopupComponent } from './fidele-delete-dialog.component';
import { IFidele } from 'app/shared/model/fidele.model';
import { FideleDetailVersementsComponent } from 'app/entities/fidele/fidele-detail-versements.component';

@Injectable({ providedIn: 'root' })
export class FideleResolve implements Resolve<IFidele> {
    constructor(private service: FideleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Fidele> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Fidele>) => response.ok),
                map((fidele: HttpResponse<Fidele>) => fidele.body)
            );
        }
        return of(new Fidele());
    }
}
@Injectable({ providedIn: 'root' })
export class FideleDtoResolve implements Resolve<IFideleDto> {
    constructor(private service: FideleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<FideleDto> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            console.log('Lancement de la recherche des versements du fidele');
            return this.service.findComplete(id).pipe(
                filter((response: HttpResponse<FideleDto>) => response.ok),
                map((fidele: HttpResponse<FideleDto>) => fidele.body)
            );
        }
        return of(new FideleDto());
    }
}

export const fideleRoute: Routes = [
    {
        path: 'fidele',
        component: FideleComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'stBarnabeApp.fidele.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'fidele/:id/view',
        component: FideleDetailComponent,
        resolve: {
            fidele: FideleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'stBarnabeApp.fidele.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'fidele/:id/versements',
        component: FideleDetailVersementsComponent,
        resolve: {
            fidele: FideleDtoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'stBarnabeApp.fidele.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'fidele/new',
        component: FideleUpdateComponent,
        resolve: {
            fidele: FideleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'stBarnabeApp.fidele.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'fidele/:id/edit',
        component: FideleUpdateComponent,
        resolve: {
            fidele: FideleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'stBarnabeApp.fidele.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fidelePopupRoute: Routes = [
    {
        path: 'fidele/:id/delete',
        component: FideleDeletePopupComponent,
        resolve: {
            fidele: FideleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'stBarnabeApp.fidele.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
