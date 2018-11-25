import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StBarnabeSharedModule } from 'app/shared';
import {
    CEBComponent,
    CEBDetailComponent,
    CEBUpdateComponent,
    CEBDeletePopupComponent,
    CEBDeleteDialogComponent,
    cEBRoute,
    cEBPopupRoute
} from './';

const ENTITY_STATES = [...cEBRoute, ...cEBPopupRoute];

@NgModule({
    imports: [StBarnabeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [CEBComponent, CEBDetailComponent, CEBUpdateComponent, CEBDeleteDialogComponent, CEBDeletePopupComponent],
    entryComponents: [CEBComponent, CEBUpdateComponent, CEBDeleteDialogComponent, CEBDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StBarnabeCEBModule {}
