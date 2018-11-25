import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StBarnabeSharedModule } from 'app/shared';
import {
    VersementComponent,
    VersementDetailComponent,
    VersementUpdateComponent,
    VersementDeletePopupComponent,
    VersementDeleteDialogComponent,
    versementRoute,
    versementPopupRoute
} from './';

const ENTITY_STATES = [...versementRoute, ...versementPopupRoute];

@NgModule({
    imports: [StBarnabeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        VersementComponent,
        VersementDetailComponent,
        VersementUpdateComponent,
        VersementDeleteDialogComponent,
        VersementDeletePopupComponent
    ],
    entryComponents: [VersementComponent, VersementUpdateComponent, VersementDeleteDialogComponent, VersementDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StBarnabeVersementModule {}
