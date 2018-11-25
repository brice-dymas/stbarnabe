import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICEB } from 'app/shared/model/ceb.model';

@Component({
    selector: 'jhi-ceb-detail',
    templateUrl: './ceb-detail.component.html'
})
export class CEBDetailComponent implements OnInit {
    cEB: ICEB;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cEB }) => {
            this.cEB = cEB;
        });
    }

    previousState() {
        window.history.back();
    }
}
