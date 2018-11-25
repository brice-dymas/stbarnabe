import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICarte } from 'app/shared/model/carte.model';
import { CarteService } from './carte.service';

@Component({
    selector: 'jhi-carte-update',
    templateUrl: './carte-update.component.html'
})
export class CarteUpdateComponent implements OnInit {
    carte: ICarte;
    isSaving: boolean;

    constructor(private carteService: CarteService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ carte }) => {
            this.carte = carte;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.carte.id !== undefined) {
            this.subscribeToSaveResponse(this.carteService.update(this.carte));
        } else {
            this.subscribeToSaveResponse(this.carteService.create(this.carte));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICarte>>) {
        result.subscribe((res: HttpResponse<ICarte>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
