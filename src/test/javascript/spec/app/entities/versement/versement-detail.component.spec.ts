/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StBarnabeTestModule } from '../../../test.module';
import { VersementDetailComponent } from 'app/entities/versement/versement-detail.component';
import { Versement } from 'app/shared/model/versement.model';

describe('Component Tests', () => {
    describe('Versement Management Detail Component', () => {
        let comp: VersementDetailComponent;
        let fixture: ComponentFixture<VersementDetailComponent>;
        const route = ({ data: of({ versement: new Versement(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [StBarnabeTestModule],
                declarations: [VersementDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(VersementDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VersementDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.versement).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
