import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFidele } from 'app/shared/model/fidele.model';

@Component({
    selector: 'jhi-fidele-detail',
    templateUrl: './fidele-detail.component.html'
})
export class FideleDetailComponent implements OnInit {
    fidele: IFidele;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ fidele }) => {
            this.fidele = fidele;
        });
    }

    previousState() {
        window.history.back();
    }
}
