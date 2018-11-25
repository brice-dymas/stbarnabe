import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVersement } from 'app/shared/model/versement.model';

@Component({
    selector: 'jhi-versement-detail',
    templateUrl: './versement-detail.component.html'
})
export class VersementDetailComponent implements OnInit {
    versement: IVersement;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ versement }) => {
            this.versement = versement;
        });
    }

    previousState() {
        window.history.back();
    }
}
