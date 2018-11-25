import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICarte } from 'app/shared/model/carte.model';

@Component({
    selector: 'jhi-carte-detail',
    templateUrl: './carte-detail.component.html'
})
export class CarteDetailComponent implements OnInit {
    carte: ICarte;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ carte }) => {
            this.carte = carte;
        });
    }

    previousState() {
        window.history.back();
    }
}
