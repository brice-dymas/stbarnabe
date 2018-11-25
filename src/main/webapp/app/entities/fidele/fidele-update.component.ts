import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IFidele } from 'app/shared/model/fidele.model';
import { FideleService } from './fidele.service';
import { ICarte } from 'app/shared/model/carte.model';
import { CarteService } from 'app/entities/carte';
import { ICEB } from 'app/shared/model/ceb.model';
import { CEBService } from 'app/entities/ceb';

@Component({
    selector: 'jhi-fidele-update',
    templateUrl: './fidele-update.component.html'
})
export class FideleUpdateComponent implements OnInit {
    fidele: IFidele;
    isSaving: boolean;

    cartes: ICarte[];

    cebs: ICEB[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private fideleService: FideleService,
        private carteService: CarteService,
        private cEBService: CEBService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ fidele }) => {
            this.fidele = fidele;
        });
        this.carteService.query().subscribe(
            (res: HttpResponse<ICarte[]>) => {
                this.cartes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.cEBService.query().subscribe(
            (res: HttpResponse<ICEB[]>) => {
                this.cebs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.fidele.id !== undefined) {
            this.subscribeToSaveResponse(this.fideleService.update(this.fidele));
        } else {
            this.subscribeToSaveResponse(this.fideleService.create(this.fidele));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFidele>>) {
        result.subscribe((res: HttpResponse<IFidele>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCarteById(index: number, item: ICarte) {
        return item.id;
    }

    trackCEBById(index: number, item: ICEB) {
        return item.id;
    }
}
