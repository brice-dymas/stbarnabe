import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IVersement } from 'app/shared/model/versement.model';
import { VersementService } from './versement.service';
import { IFidele } from 'app/shared/model/fidele.model';
import { FideleService } from 'app/entities/fidele';
import { IEmploye } from 'app/shared/model/employe.model';
import { EmployeService } from 'app/entities/employe';

@Component({
    selector: 'jhi-versement-update',
    templateUrl: './versement-update.component.html'
})
export class VersementUpdateComponent implements OnInit {
    versement: IVersement;
    isSaving: boolean;

    fideles: IFidele[];

    employes: IEmploye[];
    dateVersementDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private versementService: VersementService,
        private fideleService: FideleService,
        private employeService: EmployeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ versement }) => {
            this.versement = versement;
        });
        this.fideleService.query().subscribe(
            (res: HttpResponse<IFidele[]>) => {
                this.fideles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.employeService.query().subscribe(
            (res: HttpResponse<IEmploye[]>) => {
                this.employes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.versement.id !== undefined) {
            this.subscribeToSaveResponse(this.versementService.update(this.versement));
        } else {
            this.subscribeToSaveResponse(this.versementService.create(this.versement));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IVersement>>) {
        result.subscribe((res: HttpResponse<IVersement>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackFideleById(index: number, item: IFidele) {
        return item.id;
    }

    trackEmployeById(index: number, item: IEmploye) {
        return item.id;
    }
}
