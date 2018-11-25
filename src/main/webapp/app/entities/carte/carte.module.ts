import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StBarnabeSharedModule } from 'app/shared';
import {
    CarteComponent,
    CarteDetailComponent,
    CarteUpdateComponent,
    CarteDeletePopupComponent,
    CarteDeleteDialogComponent,
    carteRoute,
    cartePopupRoute
} from './';

const ENTITY_STATES = [...carteRoute, ...cartePopupRoute];

@NgModule({
    imports: [StBarnabeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [CarteComponent, CarteDetailComponent, CarteUpdateComponent, CarteDeleteDialogComponent, CarteDeletePopupComponent],
    entryComponents: [CarteComponent, CarteUpdateComponent, CarteDeleteDialogComponent, CarteDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StBarnabeCarteModule {}
