import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICEB } from 'app/shared/model/ceb.model';
import { CEBService } from './ceb.service';

@Component({
    selector: 'jhi-ceb-update',
    templateUrl: './ceb-update.component.html'
})
export class CEBUpdateComponent implements OnInit {
    cEB: ICEB;
    isSaving: boolean;

    constructor(private cEBService: CEBService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cEB }) => {
            this.cEB = cEB;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.cEB.id !== undefined) {
            this.subscribeToSaveResponse(this.cEBService.update(this.cEB));
        } else {
            this.subscribeToSaveResponse(this.cEBService.create(this.cEB));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICEB>>) {
        result.subscribe((res: HttpResponse<ICEB>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
