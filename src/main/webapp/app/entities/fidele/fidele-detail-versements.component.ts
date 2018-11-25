import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFideleDto } from 'app/shared/model/fideledto.model';
import { IVersement, Versement } from 'app/shared/model/versement.model';

@Component({
    selector: 'jhi-fidele-detail-versements',
    templateUrl: './fidele-detail-versements.component.html'
})
export class FideleDetailVersementsComponent implements OnInit {
    fideleDto: IFideleDto;
    versements: IVersement[];

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ fidele }) => {
            this.fideleDto = fidele;
            this.versements = fidele.versements;
            console.log('voici le fidele recuperer ', this.fideleDto);
        });
    }

    previousState() {
        window.history.back();
    }
}
